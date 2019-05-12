package com.siteminder.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailException extends RuntimeException {

    public EmailException(String message){
        log.error("Exception Occurred :{}", message);
    }
}
