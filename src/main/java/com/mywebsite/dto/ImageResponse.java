package com.mywebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageResponse {
    private String filename;
    private String filePath;
    private String oldFilename;
    private String oldFilePath;
}
