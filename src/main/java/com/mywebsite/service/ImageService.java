package com.mywebsite.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mywebsite.dto.ImageCreateResponse;
import com.mywebsite.dto.ImageResponse;
import com.mywebsite.dto.WebResponse;
import com.mywebsite.model.HistoryImage;
import com.mywebsite.model.Image;
import com.mywebsite.repository.HistoryImageRepository;
import com.mywebsite.repository.ImageRepository;

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
    private ImageCreateResponse toCreateImageResponse(Image image, HistoryImage historyImage){
        return ImageCreateResponse.builder()
        .filename(image.getFilename())
        .filePath(image.getFilepath())
        .oldFilename(historyImage.getCurrentFilename())
        .oldFilePath(historyImage.getCurrentFilepath())
        .kategory(image.getKategory())
        .build();
    }

    private ImageResponse toImageResponse(Image image){
        return ImageResponse.builder()
        .filename(image.getFilename())
        .filepath(image.getFilepath())
        .kategory(image.getKategory())
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
    public WebResponse upload(MultipartFile request, String kategory){

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
            List<Image> imageData = this.imageRepository.findByFilename(request.getOriginalFilename());
            
            // Chek apakah file sudah ada findByFilenamepada server
            if (destination.exists() && !imageData.isEmpty()) {
                // Move File to archive
                File pathUpdate = new File(imagePath + "/archive/", dateTime + request.getOriginalFilename());
                destination.renameTo(pathUpdate);

                // upload image to direktory
                request.transferTo(destination);

                // write to db history file
                historyImage.setCurrentFilename(pathUpdate.getName());
                historyImage.setCurrentFilepath(imagePath + "/archive/");
                historyImage.setOldFilename(request.getOriginalFilename());
                historyImage.setOldFilepath(imagePath + "/active/");
                historyImage.setCreateOn(new Date());
                historyImage.setCreateBy("admin");

                historyImage = historyImageRepository.save(historyImage);
                System.out.println("update");

                int dataUpdate = imageRepository.update(kategory, "admin", new Date(), request.getOriginalFilename());
                System.out.println("data update : " + dataUpdate);

                List<Image> imageUpdate = this.imageRepository.findByFilename(imageData.get(0).getFilename());

                return toWebResponse("success", "Success upload file", toCreateImageResponse(imageUpdate.get(0), historyImage));
            }

            // upload image to direktory
            request.transferTo(destination);

            // pencatatan ke database
            image.setFilename(request.getOriginalFilename());
            image.setKategory(kategory);
            image.setFilepath(imagePath + "/active/");
            image.setCreateOn(new Date());
            image.setCreateBy("admin");

            image = imageRepository.save(image);
        
            return toWebResponse("success", "Success upload file", toCreateImageResponse(image, historyImage));
        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), null);
        }
    }
    // Read
    // get by kategory id
    @Transactional(readOnly = true)
    public WebResponse getByKategoryId(String kategory){
        try {
            List<Image> image = this.imageRepository.findByKategory(kategory);
            if (image.isEmpty()) {
                return toWebResponse("success", "Data not found with kategory like " + kategory, null);
            }
            return toWebResponse("success", "Success get data", image.stream().map(this::toImageResponse).toList());
        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), null);
        }
    }
    // Update
    // Delete
}
