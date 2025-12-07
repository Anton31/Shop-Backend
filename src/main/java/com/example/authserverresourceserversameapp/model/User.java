package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<Order> orders = new ArrayList<>();
    @Id
    @SequenceGenerator(name = "userGen", sequenceName = "userSeq", initialValue = 10)
    @GeneratedValue(generator = "userGen")
    private long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Role role;

    public User() {
        this.enabled = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof User user)) return false;

        return getUsername().equals(user.getUsername()) && getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }
}
