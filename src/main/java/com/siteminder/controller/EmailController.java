package com.siteminder.controller;


import com.siteminder.model.EmailRequest;
import com.siteminder.service.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
//@EnableCircuitBreaker
public class EmailController {

    @Autowired
    EmailSendService emailService;


    @PostMapping("/sendemail")
    public ResponseEntity sendEmail(@Valid @RequestBody EmailRequest emailRequest)
    {
        boolean result = emailService.sendEmail(emailRequest);
        if(!result){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
