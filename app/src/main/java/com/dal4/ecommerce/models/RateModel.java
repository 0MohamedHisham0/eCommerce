package com.dal4.ecommerce.models;

public class RateModel {
    private String message;

    public RateModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
