package com.mywebsite.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MessageRequest {
    @Valid
    
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Email
    @NotBlank(message = "Email must not be blank")
    private String email;
    
    @NotBlank(message = "Message must not be blank")
    private String message;
}
