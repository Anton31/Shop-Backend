package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    Page<Product> getAllByTypeId(Long typeId, Pageable pageable);

    Page<Product> getAllByBrandId(Long brandId, Pageable pageable);

    Page<Product> getAllByTypeIdAndBrandId(Long typeId, Long brandId, Pageable pageable);

    List<Product> getAllByTypeIdAndBrandId(Long typeId, Long brandId);
}
