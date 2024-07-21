package com.mywebsite.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mywebsite.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{

    List<Image> findByFilename(String filename);

    List<Image> findByKategory(String kategory);

    @Modifying
    @Query(value = "update image set kategory=?1, create_by=?2, create_on=?3 where filename=?4", nativeQuery = true)
    int update(String kategory, String create_by, Date create_on, String filename);
}
