package com.example.authserverresourceserversameapp.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class PhotoDto {
    private long productId;

    private List<MultipartFile> photos;

    public PhotoDto() {
    }

    public PhotoDto(long productId, List<MultipartFile> photos) {
        this.productId = productId;
        this.photos = photos;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }
}
