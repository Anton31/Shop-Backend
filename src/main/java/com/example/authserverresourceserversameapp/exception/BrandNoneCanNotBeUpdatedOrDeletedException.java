package com.example.authserverresourceserversameapp.exception;

public class BrandNoneCanNotBeUpdatedOrDeletedException extends RuntimeException {

    private final String message;

    public BrandNoneCanNotBeUpdatedOrDeletedException() {

        this.message = "Brand \"None\" can not be updated or deleted!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
