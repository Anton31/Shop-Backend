package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @SequenceGenerator(name = "productGen", sequenceName = "productSeq", initialValue = 20)
    @GeneratedValue(generator = "productGen")
    private Long id;
    private String name;
    private int price;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Photo> photos = new ArrayList<>();
    @ManyToOne
    private Type type;
    @ManyToOne
    private Brand brand;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Item> items = new ArrayList<>();

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.setProduct(this);
    }

    public void removePhoto(Photo photo) {
        this.photos.remove(photo);
        photo.setProduct(null);
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.setProduct(this);
    }
}
