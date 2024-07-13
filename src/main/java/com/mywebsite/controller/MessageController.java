package com.mywebsite.controller;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mywebsite.dto.MessageRequest;
import com.mywebsite.dto.WebResponse;
import com.mywebsite.service.MessageService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/message/")
public class MessageController {
    
    private MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    // Create
    @PostMapping("send")
    public WebResponse sendMessage(@Valid @RequestBody MessageRequest request, Errors errors){
        return messageService.create(request, errors);
    }
    // read
    // update
    // Delete
}
