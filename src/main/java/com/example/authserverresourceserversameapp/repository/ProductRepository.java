package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    List<Product> getAllByTypeId(Long typeId, Sort sort);

    List<Product> getAllByBrandId(Long brandId, Sort sort);

    List<Product> getAllByTypeIdAndBrandId(Long typeId, Long brandId, Sort sort);

}
