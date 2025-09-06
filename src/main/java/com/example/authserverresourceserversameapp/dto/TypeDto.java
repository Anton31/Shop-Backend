package com.example.authserverresourceserversameapp.dto;


import jakarta.validation.constraints.NotBlank;

public class TypeDto {
    private Long id;
    private Long brandId;
    @NotBlank(message = "Type name is mandatory")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
