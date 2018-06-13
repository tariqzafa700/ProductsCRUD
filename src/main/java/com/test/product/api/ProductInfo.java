package com.test.product.api;

public class ProductInfo {
    private Long productId;

    private String name;

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

}
