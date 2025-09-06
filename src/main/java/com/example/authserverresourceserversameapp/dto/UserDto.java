package com.example.authserverresourceserversameapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {
    private String role;
    private String username;
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 4, max = 16, message = "password size should be between 4 and 16")
    private String password;

    private String passwordConfirmed;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }
}
