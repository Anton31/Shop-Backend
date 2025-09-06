package com.example.authserverresourceserversameapp.exception;


public class TypeExistsException extends RuntimeException {
    private final String message;

    public TypeExistsException(String name) {
        this.message = "Type with name: \"" + name + "\" already exists!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
