package com.mywebsite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    // Create
    @PostMapping("send")
    public WebResponse sendMessage(@Valid @RequestBody MessageRequest request, Errors errors){
        logger.info("MessageController sendMessage request >> {}", request);
        WebResponse response = messageService.create(request, errors);
        logger.info("MessageController sendMessage response >> {}", response);
        return response;
    }
    // read
    // update
    // Delete
}
