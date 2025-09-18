package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type getOneByName(String name);

    List<Type> getAllByNameNotLike(String name, Sort sort);

    @Query(value = "select t from Type t where size(t.products)>0 and t.name not like ?1")
    List<Type> getProductTypes(String name);
}
