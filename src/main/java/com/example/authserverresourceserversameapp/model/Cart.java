package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Cart {
    @Id
    @SequenceGenerator(name = "cartGen", sequenceName = "cartSeq", initialValue = 10)
    @GeneratedValue(generator = "cartGen")
    private long id;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<Item> items = new ArrayList<>();

    public Cart() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
