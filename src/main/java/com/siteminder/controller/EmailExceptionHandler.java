package com.siteminder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(assignableTypes = {EmailController.class})
@RestController
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EmailExceptionHandler {

    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    @ExceptionHandler(value = Exception.class)
    public void handleException(Exception exception) {
        log.error(exception.getMessage());
    }
}
