package com.example.authserverresourceserversameapp.dto;


import jakarta.validation.constraints.NotBlank;

public class BrandDto {
    private Long id;
    private Long typeId;
    @NotBlank(message = "Brand name is mandatory")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
