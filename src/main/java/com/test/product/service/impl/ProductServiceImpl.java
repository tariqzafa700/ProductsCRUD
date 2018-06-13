package com.test.product.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.product.api.ProductInfo;
import com.test.product.model.Product;
import com.test.product.repository.ProductRepository;
import com.test.product.service.ProductService;
import com.test.product.service.exception.ServiceException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private Product convertObjectToDTO(ProductInfo pInfo) {
        Product product = new Product();
        product.setName(pInfo.getName());
        product.setQuantity(pInfo.getQuantity());
        return product;
    }
    
    private ProductInfo convertDTOToObject(Product product) {
        ProductInfo pInfo = new ProductInfo();
        pInfo.setName(product.getName());
        pInfo.setQuantity(product.getQuantity());
        pInfo.setProductId(product.getProductId());
        return pInfo;
    }
    
    @Override
    public Long createProduct(ProductInfo pInfo) throws ServiceException {
        try {
            Product product =convertObjectToDTO(pInfo);
            if(product.getName() == null || "".equals(product.getName()) || product.getQuantity() == null )
                throw new ServiceException("Invalid parameters", -3);
            Product created = productRepository.saveAndFlush(product);
            return created.getProductId();
        } catch (Exception e) {
            throw new ServiceException("Duplicate product " + pInfo.getName(), -2);
        }
    }

    @Override
    public Long updateProduct(Long id, ProductInfo pInfo) throws ServiceException {
        Product product = convertObjectToDTO(pInfo);
        if(product.getName() == null || "".equals(product.getName()) || product.getQuantity() == null )
            throw new ServiceException("Invalid parameters", -3);
        Product exists = productRepository.findById(id)
                .orElseThrow(() -> new ServiceException("product with id " + id + " not found", -1));
        if (product.getName() != null)
            exists.setName(product.getName());
        if (product.getQuantity() != null)
            exists.setQuantity(product.getQuantity());
        return productRepository.saveAndFlush(exists).getProductId();
    }

    @Override
    public void deleteProduct(Long id) throws ServiceException {
        productRepository.findById(id)
                .orElseThrow(() -> new ServiceException("product with id " + id + " not found", -1));
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductInfo> getAllProducts() throws JsonProcessingException {
        return productRepository.findAll().stream().map(product -> convertDTOToObject(product)).collect(Collectors.toList());
    }
}
