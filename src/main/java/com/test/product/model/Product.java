package com.test.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", unique = true)
    @NotNull
    private String name;

    @Column(name = "product_quantity")
    @NotNull
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product = " + this.getProductId() + "  " + this.getName() + "  " + this.getQuantity();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Product && this.getName().equals(((Product) obj).getName())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hCode = prime * (this.getName() == null ? 0 : this.getName().hashCode());
        hCode = hCode + prime * this.getQuantity();
        return hCode;
    }
}
