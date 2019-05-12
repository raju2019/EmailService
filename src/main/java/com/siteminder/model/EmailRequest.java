package com.siteminder.model;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class EmailRequest {

    @NotNull
    @Email(message="Invalid email")
    private String to;

    @Email(message="Invalid email")
    private String cc;

    @Email(message="Invalid email")
    private String bcc ;

    @NotNull
    private String subject;

    @NotNull
    private String body;

}
