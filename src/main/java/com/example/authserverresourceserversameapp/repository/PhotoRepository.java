package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Photo;
import com.example.authserverresourceserversameapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    boolean existsByUrl(String url);

    void deleteAllByProduct(Product product);
}
