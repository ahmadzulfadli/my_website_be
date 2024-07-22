package com.mywebsite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mywebsite.dto.WebResponse;
import com.mywebsite.service.ImageService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/image/")
public class ImageControler {

    private ImageService imageService;

    Logger logger = LoggerFactory.getLogger(ImageControler.class);

    public ImageControler(ImageService imageService){
        this.imageService = imageService;
    }

    // Create
    @PostMapping("upload")
    public WebResponse upload(@RequestParam("image") MultipartFile image, @RequestParam("kategory") String kategory){
        logger.info("ImageController upload request >> {} and {}", image, kategory);
        WebResponse response = imageService.upload(image, kategory);
        logger.info("ImageController upload response >> {}", response);
        return response;
    }
    
    // Read
    @GetMapping("kategory")
    public WebResponse getByKategory(@RequestParam("kategory") String kategory){
        logger.info("ImageController getByKategory request >> {}", kategory);
        WebResponse response = imageService.getByKategoryId(kategory);
        logger.info("ImageController getByKategory response >> {}", response);
        return response;

    }
    // Update
    // Delete
}
