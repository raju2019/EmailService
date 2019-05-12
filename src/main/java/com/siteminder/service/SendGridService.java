package com.siteminder.service;

import com.sendgrid.*;
import com.siteminder.model.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Qualifier("sendGrid")
@PropertySource("classpath:sendgrid.properties")
@Slf4j
public class SendGridService implements EmailService{

    @Value("${apikey}")
    private String apikey;

    @Value("${endpoint}")
    private String endpoint;


    public boolean sendEmail(EmailRequest emailRequest)  {
        log.info("Sending email using SendGrid implementation");
        boolean emailSent = true;
        Email from = new Email("rajnsyndey@gmail.com");
        String subject = emailRequest.getSubject();
        Personalization personalization = new Personalization();
        Email to = new Email(emailRequest.getTo());
        personalization.addTo(to);
        Email cc = new Email(emailRequest.getCc());
        personalization.addCc(cc);
        Email bcc = new Email(emailRequest.getBcc());
        personalization.addBcc(bcc);

        Content content = new Content("text/plain", emailRequest.getBody());
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addPersonalization(personalization);
        mail.addContent(content);

        SendGrid sg = new SendGrid(apikey);
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint(endpoint);
            request.setBody(mail.build());
            response = sg.api(request);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            emailSent=false;
        }
        log.info("Status :" + response.getStatusCode()+" Body "+ response.getBody()+" Headers "+ response.getHeaders());

        return emailSent;
    }
}
