package com.mywebsite.service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.mywebsite.dto.MessageRequest;
import com.mywebsite.dto.MessageResponse;
import com.mywebsite.dto.WebResponse;
import com.mywebsite.model.Message;
import com.mywebsite.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    // Response
    public MessageResponse toMessageResponse(Message response){
        return MessageResponse.builder()
        .name(response.getName())
        .email(response.getEmail())
        .message(response.getMessage())
        .build();
    }

    public WebResponse toWebResponse(String status, Object message, Object data){
        return WebResponse.builder()
        .status(status)
        .message(message)
        .data(data)
        .build();
    }

    // Create
    public WebResponse create(MessageRequest request, Errors errors){

        // Validation
        if (errors.hasErrors()) {
            List<Object> error = new ArrayList<>();
            for (ObjectError err : errors.getAllErrors()) {
                error.add(err.getDefaultMessage());
            }
            return toWebResponse("failed", error, null);
        }
        // End Validation

        try {
            Message message = new Message();

            message.setName(request.getName());
            message.setEmail(request.getEmail());
            message.setMessage(request.getMessage());
            message.setCreateBy(request.getName().toLowerCase());
            message.setCreateOn(new Date());

            Message messageSaved = this.messageRepository.save(message);

            return toWebResponse("success", "Data Successfuly sended",toMessageResponse(messageSaved));
        } catch (Exception e) {
            return toWebResponse("failed", e.getMessage(), null);
        }

        
    }

    // read
    // update
    // Delete
}
