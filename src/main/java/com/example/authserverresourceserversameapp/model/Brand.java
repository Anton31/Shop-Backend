package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Brand {
    @Id
    @SequenceGenerator(name = "brandGen", sequenceName = "brandSeq", initialValue = 20)
    @GeneratedValue(generator = "brandGen")
    private long id;
    private String name;

    @ManyToMany
    @JoinTable(name = "brand_type", joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id")
    )
    private List<Type> types = new ArrayList<>();

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

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public void addType(Type type) {
        this.types.add(type);
    }

    public void removeType(Type type) {
        this.types.remove(type);
    }

}
