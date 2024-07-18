package com.mywebsite.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mywebsite.dto.ImageResponse;
import com.mywebsite.dto.WebResponse;
import com.mywebsite.model.HistoryImage;
import com.mywebsite.model.Image;
import com.mywebsite.repository.HistoryImageRepository;
import com.mywebsite.repository.ImageRepository;

import jakarta.transaction.Transactional;

@Service
public class ImageService {

    private String imagePath = "/home/ubuntu/data/image/mywebsite";

    private ImageRepository imageRepository;
    private HistoryImageRepository historyImageRepository;

    public ImageService(ImageRepository imageRepository, HistoryImageRepository historyImageRepository){
        this.imageRepository = imageRepository;
        this.historyImageRepository = historyImageRepository;
    }
    
    // Response
    private ImageResponse toImageResponse(Image image, HistoryImage historyImage){
        return ImageResponse.builder()
        .filename(image.getFilename())
        .filePath(image.getFilepath())
        .oldFilename(historyImage.getCurrentFilename())
        .oldFilePath(historyImage.getCurrentFilepath())
        .build();
    }

    private WebResponse toWebResponse(String status, Object message, Object data){
        return WebResponse.builder()
        .status(status)
        .message(message)
        .data(data)
        .build();
    }

    // Create
    @Transactional
    public WebResponse upload(MultipartFile request){

        LocalDateTime now =LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String dateTime = now.format(formatter);

        if (request.isEmpty()) {
            return toWebResponse("failed", "File is empty", null);
        }

        try {
            File destination = new File(imagePath + "/active/", request.getOriginalFilename());

            HistoryImage historyImage = new HistoryImage();
            Image image = new Image();
            
            // Chek apakah file sudah ada pada server
            if (destination.exists()) {
                // Move File to archive
                File pathUpdate = new File(imagePath + "/archive/", dateTime + request.getOriginalFilename());
                destination.renameTo(pathUpdate);

                // mencatat ke db history file
                historyImage.setCurrentFilename(pathUpdate.getName());
                historyImage.setCurrentFilepath(imagePath + "/archive/");
                historyImage.setOldFilename(request.getOriginalFilename());
                historyImage.setOldFilepath(imagePath + "/active/");
                historyImage.setCreateOn(new Date());
                historyImage.setCreateBy("admin");

                historyImage = historyImageRepository.save(historyImage);
            }

            // upload image to direktory
            request.transferTo(destination);

            // pencatatan ke database
            image.setFilename(request.getOriginalFilename());
            image.setFilepath(imagePath);
            image.setCreateOn(new Date());
            image.setCreateBy("admin");

            image = imageRepository.save(image);
        
            return toWebResponse("success", "Success upload file", toImageResponse(image, historyImage));
        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), null);
        }
    }
    // Read
    // Update
    // Delete
}
