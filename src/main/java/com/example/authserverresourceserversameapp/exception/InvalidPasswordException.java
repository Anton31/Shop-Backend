package com.example.authserverresourceserversameapp.exception;

public class InvalidPasswordException extends RuntimeException {
    private String message;

    public InvalidPasswordException() {
        this.message = "password should contain at list one digit and one capital letter";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
