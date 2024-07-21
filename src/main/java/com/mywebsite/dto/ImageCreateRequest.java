package com.mywebsite.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageCreateRequest {

    private MultipartFile file;
}