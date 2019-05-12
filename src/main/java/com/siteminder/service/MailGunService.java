package com.siteminder.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.siteminder.exception.EmailException;
import com.siteminder.model.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mailGun")
@PropertySource("classpath:mailgun.properties")
@Slf4j
public class MailGunService  implements EmailService{

    @Value("${domain}")
    private String domain;

    @Value("${apikey}")
    private String apikey;

    @Value("${url}")
    private String url;

    HttpResponse<String> response;

    @Override
    public boolean sendEmail(EmailRequest emailRequest){
        log.info("Sending email using MailGun implementation with url:{} and domain:{}", url, domain);
        boolean emailSent = true;

        try {
            response = Unirest.post(url + domain + "/messages")
                    .basicAuth("api", apikey)
                    .field("from", "rajnsydney@gmail.com")
                    .field("to", emailRequest.getTo())
                    .field("cc", emailRequest.getCc())
                    .field("bcc", emailRequest.getBcc())
                    .field("subject", emailRequest.getSubject())
                    .field("text", emailRequest.getBody())
                    .asString();
        } catch (UnirestException e) {
            emailSent = false;
        }

        if(!emailSent || response.getStatus()!= HttpStatus.OK.value()) {
            throw new EmailException("Exception while sending email using Mailgun");
        }

        log.info("Body is:{} and  status is :{} and emailsentStatus is :{}"+  response.getBody(), response.getStatusText(), emailSent);
        return emailSent;

    }

}
