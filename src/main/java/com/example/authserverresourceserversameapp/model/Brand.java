package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany(mappedBy = "brands")
    @JsonIgnore
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

    @PreRemove
    public void removeTypeAssociations() {
        List<Type> toDelete = new ArrayList<>(this.types);
        for (Type type : toDelete) {
            type.removeBrand(this);
            this.getTypes().remove(type);
        }
    }
}
