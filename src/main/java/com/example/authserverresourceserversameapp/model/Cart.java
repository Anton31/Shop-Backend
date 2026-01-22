package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Cart {
    @Id
    @SequenceGenerator(name = "cartGen", sequenceName = "cartSeq", initialValue = 20)
    @GeneratedValue(generator = "cartGen")
    private Long id;
    private transient List<Long> cartProductsIds = new ArrayList<>();
    private transient long totalPrice;
    private transient long totalQuantity;
    @OneToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<Item> items = new ArrayList<>();

    public Cart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Long> getCartProductsIds() {
        this.items.forEach(x -> this.cartProductsIds.add(x.getProduct().getId()));
        return this.cartProductsIds;
    }

    public long getTotalPrice() {
        for (Item item : this.items) {
            this.totalPrice += item.getTotalPrice();
        }
        return this.totalPrice;
    }

    public long getTotalQuantity() {
        for (Item item : this.items) {
            this.totalQuantity += item.getQuantity();
        }
        return this.totalQuantity;
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.setCart(this);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
        item.setCart(null);
    }
}
