package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Photo findByFileNameAndProductId(String fileName, long productId);

}
