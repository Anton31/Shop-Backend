package com.example.authserverresourceserversameapp.exception;

public class PasswordsDontMatchException extends RuntimeException {

    private final String message;

    public PasswordsDontMatchException() {
        this.message = "passwords don't match!";
    }

    @Override
    public String getMessage() {
        return message;
    }


}
