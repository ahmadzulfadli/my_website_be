package com.mywebsite.controller;

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

    public ImageControler(ImageService imageService){
        this.imageService = imageService;
    }

    // Create
    @PostMapping("upload")
    public WebResponse upload(@RequestParam("image") MultipartFile request, @RequestParam("kategory") String kategory){
        return imageService.upload(request, kategory);
    }
    
    // Read
    @GetMapping("kategory")
    public WebResponse getByKategory(@RequestParam("kategory") String kategory){
        return imageService.getByKategoryId(kategory);
    }
    // Update
    // Delete
}
