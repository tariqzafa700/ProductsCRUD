package com.test.product.service.exception;

public class ServiceException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -1530741295579564005L;

    private String message;
    
    private Integer errorCode;

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.errorCode = code;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
