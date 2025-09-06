package com.example.authserverresourceserversameapp.exception;


public class UserExistsException extends RuntimeException {

    private final String message;

    public UserExistsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
