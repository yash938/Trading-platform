package com.tradingplatform.service;

import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {

    public void sendVerification(String email,String otp) throws MessagingException;


}

