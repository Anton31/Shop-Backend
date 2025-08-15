package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Brand;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand getOneByName(String name);

    List<Brand> getAllByNameNotLike(String name, Sort sort);

    List<Brand> getAllByTypesIdAndNameNotLike(long typeId, String name, Sort sort);
}


