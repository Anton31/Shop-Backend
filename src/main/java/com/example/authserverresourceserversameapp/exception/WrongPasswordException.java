package com.example.authserverresourceserversameapp.exception;

public class WrongPasswordException extends RuntimeException{
    private final String message;

    public WrongPasswordException() {
        this.message = "Wrong password";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
