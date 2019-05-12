package com.siteminder.service;

import com.siteminder.exception.EmailException;
import com.siteminder.model.EmailRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EmailSendServiceTest {

    @Mock
    @Qualifier("sendGrid")
    EmailService sendGridEmailService;

    @Mock
    @Qualifier("mailGun")
    EmailService mailGunEmailService;


    @InjectMocks
    EmailSendService emailSendService;

    EmailRequest emailRequest;


    @Before
    public void setEmailRequest(){
        emailRequest = new EmailRequest();
        emailRequest.setTo("test1@gmail.com");
        emailRequest.setBody("Some sample Email Body");
    }

    @Test
    public void testEmailService(){

        emailSendService.sendEmail(emailRequest);

        Mockito.verify(mailGunEmailService, times(1)).sendEmail(emailRequest);
        Mockito.verify(sendGridEmailService, times(0)).sendEmail(emailRequest);
    }

    @Test
    public void testEmailServiceFailover(){

        when(mailGunEmailService.sendEmail(emailRequest)).thenThrow(new EmailException("Some"));
        emailSendService.sendEmail(emailRequest);

        Mockito.verify(sendGridEmailService, times(1)).sendEmail(emailRequest);
    }


    @Test(expected = EmailException.class)
    public void testEmailServiceWhenAllservicesFail(){

        when(mailGunEmailService.sendEmail(emailRequest)).thenThrow(new EmailException("MailGun Errro"));
        when(sendGridEmailService.sendEmail(emailRequest)).thenThrow(new EmailException("SendGrid Error"));
        emailSendService.sendEmail(emailRequest);

        Mockito.verify(sendGridEmailService, times(1)).sendEmail(emailRequest);
        Mockito.verify(mailGunEmailService, times(1)).sendEmail(emailRequest);
    }


}