package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @SequenceGenerator(name = "productGen", sequenceName = "productSeq", initialValue = 20)
    @GeneratedValue(generator = "productGen")
    private Long id;
    private String name;
    private int price;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @OrderBy("name")
    private List<Photo> photos = new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Type type;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Brand brand;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<CartItem> items = new ArrayList<>();

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.setProduct(this);
    }

    public void removePhoto(Photo photo) {
        this.photos.remove(photo);
        photo.setProduct(null);
    }

    public void addCartItem(CartItem cartItem) {
        this.items.add(cartItem);
        cartItem.setProduct(this);
    }

    public void removeCartItem(CartItem cartItem) {
        this.items.remove(cartItem);
        cartItem.setProduct(null);
    }
}
