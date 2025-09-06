package com.example.authserverresourceserversameapp.exception;


public class ProductExistsException extends RuntimeException {

    private final String message;

    public ProductExistsException(String name) {
        this.message = "Product with name: \"" + name + "\" already exists!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
