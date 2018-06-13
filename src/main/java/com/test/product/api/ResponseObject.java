package com.test.product.api;

public class ResponseObject {

    private Long id;

    private String action;

    public ResponseObject() {

    }

    public ResponseObject(Long id, String action) {
        this.id = id;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
