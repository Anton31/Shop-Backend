package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Type {
    @Id
    @SequenceGenerator(name = "typeGen", sequenceName = "typeSeq", initialValue = 20)
    @GeneratedValue(generator = "typeGen")
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
