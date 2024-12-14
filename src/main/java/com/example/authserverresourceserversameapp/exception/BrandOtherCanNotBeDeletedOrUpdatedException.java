package com.example.authserverresourceserversameapp.exception;


public class BrandOtherCanNotBeDeletedOrUpdatedException extends RuntimeException {

    private String message;

    public BrandOtherCanNotBeDeletedOrUpdatedException() {
        this.message = "Brand \"No brand\" can't be deleted or updated!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
