package com.siteminder.service;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.siteminder.exception.EmailException;
import com.siteminder.model.EmailRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;


@Service
@Slf4j
public class EmailSendService {

    @Autowired
    @Qualifier("sendGrid")
    EmailService sendGridEmailService;

    @Autowired
    @Qualifier("mailGun")
    EmailService mailGunEmailService;

    boolean result;


    public boolean sendEmail(@Valid EmailRequest emailRequest){

        try {
            CompletableFuture<Boolean> completableFuture = CompletableFuture
                    .supplyAsync(() -> {
                        log.info("Executing through Mailgun Implementation ...");
                        return sendEmailUsingMailGun(emailRequest);
                    }).exceptionally(exception -> {
                        log.info("Executing through SendGrid Implementation ...");
                        return sendEmailUsingSendGrid(emailRequest);
                    });
            sleep(3000);
            if(completableFuture.isCompletedExceptionally()) {
                log.info("Completed using Failover Implementation");
            } else {
                log.info("Completed using Normal Implementation");
            }
            result = completableFuture.get();
        } catch (Exception e){
            throw new EmailException("Exception in sending EMAIL could not sent using either email provders");
        }
        return result;
    }


    // Hysterix implementation using Circuit breaker Pattern

   /* @HystrixCommand(fallbackMethod = "sendEmailUsingSendGrid")
    public boolean sendEmail(@Valid EmailRequest emailRequest) {
        return sendEmailUsingMailGun(emailRequest);
    }
    */

    public boolean sendEmailUsingMailGun(EmailRequest emailRequest){
        return mailGunEmailService.sendEmail(emailRequest);
    }

    public boolean sendEmailUsingSendGrid(EmailRequest emailRequest){
        return sendGridEmailService.sendEmail(emailRequest);
    }
}
