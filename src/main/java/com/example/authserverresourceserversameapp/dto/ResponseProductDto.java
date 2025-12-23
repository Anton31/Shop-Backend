package com.example.authserverresourceserversameapp.dto;

import com.example.authserverresourceserversameapp.model.Product;

import java.util.List;

public class ResponseProductDto {
    private List<Product> products;
    private long totalProducts;

    public ResponseProductDto() {
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }
}
