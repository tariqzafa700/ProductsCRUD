package com.test.product.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.product.api.ProductInfo;
import com.test.product.service.exception.ServiceException;

public interface ProductService {

    List<ProductInfo> getAllProducts() throws JsonProcessingException;

    void deleteProduct(Long id) throws ServiceException;

    Long updateProduct(Long id, ProductInfo product) throws ServiceException;

    Long createProduct(ProductInfo product) throws ServiceException;

}
