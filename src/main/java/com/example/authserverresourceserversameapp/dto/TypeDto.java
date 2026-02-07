package com.example.authserverresourceserversameapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TypeDto {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 20, message = "name must be between 3 and 20 characters")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
