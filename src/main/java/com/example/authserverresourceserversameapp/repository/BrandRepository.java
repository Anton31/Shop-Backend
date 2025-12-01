package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Brand;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand getOneByName(String name);

    List<Brand> getAllByIdAfter(long id, Sort sort);

    List<Brand> getAllByIdAfterAndTypesId(long id, long typeId, Sort sort);

}


