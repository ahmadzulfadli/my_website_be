package com.mywebsite.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mywebsite.dto.ImageResponse;
import com.mywebsite.dto.WebResponse;
import com.mywebsite.model.HistoryImage;
import com.mywebsite.model.Image;

import jakarta.transaction.Transactional;

@Service
public class ImageService {

    private String rootLocation = "/image/";
    
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
    public WebResponse create(MultipartFile request){

        if (request.isEmpty()) {
            return toWebResponse("failed", "File is empty", null);
        }
        try {
            Path destination = Paths.get(rootLocation);
            try(InputStream inputStream = request.getInputStream()){
                Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            }
            return toWebResponse("success", "Success upload file", toImageResponse(null, null));
        } catch (Exception e) {
            return toWebResponse("failed", "Error upload file", null);
        }

        
    }
    // Read
    // Update
    // Delete
}
