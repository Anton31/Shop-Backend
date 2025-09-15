package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Item {
    @Id
    @SequenceGenerator(name = "itemGen", sequenceName = "itemSeq", initialValue = 10)
    @GeneratedValue(generator = "itemGen")
    private long id;
    private long quantity;
    private transient long totalPrice;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Product product;
    @ManyToOne
    @JsonIgnore
    private Cart cart;
    @ManyToOne
    @JsonIgnore
    private Order order;

    public Item() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public long getTotalPrice() {
        return this.quantity * this.product.getPrice();
    }
}
