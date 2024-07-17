package com.mywebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mywebsite.model.HistoryImage;

@Repository
public interface HistoryImageRepository extends JpaRepository<HistoryImage, Long>{
    
}
