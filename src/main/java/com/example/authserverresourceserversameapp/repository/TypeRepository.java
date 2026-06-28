package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type getOneByName(String name);

    List<Type> getAllByIdAfter(long id, Sort sort);

    @Query("select t from Product p join p.type t where t.id>1 order by t.name")
    List<Type> getProductTypes();

}
