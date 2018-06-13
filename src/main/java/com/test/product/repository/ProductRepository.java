package com.test.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
