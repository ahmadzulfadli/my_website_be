package com.mywebsite.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageRequest {
    @Valid

    @NotEmpty(message = "File not be null")
    private MultipartFile file;
}