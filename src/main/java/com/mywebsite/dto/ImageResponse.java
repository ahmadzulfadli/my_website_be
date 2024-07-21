package com.mywebsite.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Builder
public class ImageResponse {
    private String filename;
    private String filepath;
    private String kategory;
}
