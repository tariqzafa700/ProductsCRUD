package com.test.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.product.api.ProductInfo;
import com.test.product.api.ResponseObject;
import com.test.product.service.ProductService;
import com.test.product.service.exception.ServiceException;

@EnableAutoConfiguration
@RequestMapping("/products")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;
    


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseObject> addProduct(@RequestBody ProductInfo product) {
        Long id = null;
        try {
            id = productService.createProduct(product);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(id, "created"));
    }

    @RequestMapping(value = "{productId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseObject> updateProduct(@PathVariable("productId") Long productId,
            @RequestBody ProductInfo product) {
        Long id;
        try {
            id = productService.updateProduct(productId, product);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(id, "updated"));
    }

    @RequestMapping(value = "{productId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable("productId") Long productId) {
        try {
            productService.deleteProduct(productId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(productId, "deleted"));
    }

    @RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<List<ProductInfo>> getAllProducts() {
        List<ProductInfo> products;
        try {
            products = productService.getAllProducts();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}

