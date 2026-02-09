package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @SequenceGenerator(name = "roleGen", sequenceName = "roleSeq", initialValue = 10)
    @GeneratedValue(generator = "roleGen")
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
        if (this.name.equals("admin") && !user.getRoles().isEmpty()) {
            user.getRoles().set(0, this);
        } else {
            user.getRoles().add(this);
        }
    }
}
