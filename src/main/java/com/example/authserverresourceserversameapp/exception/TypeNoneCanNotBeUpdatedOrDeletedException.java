package com.example.authserverresourceserversameapp.exception;

public class TypeNoneCanNotBeUpdatedOrDeletedException extends RuntimeException {

    private final String message;

    public TypeNoneCanNotBeUpdatedOrDeletedException() {
        this.message = "Type \"None\" can not be updated or deleted!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
