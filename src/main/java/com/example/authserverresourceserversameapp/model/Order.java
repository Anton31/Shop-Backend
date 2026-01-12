package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @SequenceGenerator(name = "orderGen", sequenceName = "orderSeq", initialValue = 20)
    @GeneratedValue(generator = "orderGen")
    private Long id;
    private String description;
    private String username;
    private String email;
    private transient long totalPrice;
    private transient long totalQuantity;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<Item> items = new ArrayList<>();

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void addItem(Item item) {
        item.setOrder(this);
        this.items.add(item);
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
}
