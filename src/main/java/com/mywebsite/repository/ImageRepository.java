package com.mywebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mywebsite.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
    @Query(value="select * from image where filename = ?1", nativeQuery = true)
    Image findByFilename(String filename);
}
