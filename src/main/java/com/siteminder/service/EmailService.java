package com.siteminder.service;

import com.siteminder.model.EmailRequest;

public interface EmailService {

    boolean sendEmail(EmailRequest emailRequest);
}
