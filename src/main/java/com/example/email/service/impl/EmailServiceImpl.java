package com.example.email.service.impl;

import com.example.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String SUBJECT_THE_MANOR = "New tennis coach at The Manor Golf and Country Club";
    @Value("${spring.mail.verify.host")
    private String host;
    @Value("${spring.mail.username")
    private String fromEmail;
    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleMailMessage(String firstName, String lastName, String to, String body) {
        String name = firstName + " " + lastName;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(SUBJECT_THE_MANOR);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText("This is a test of the body.");
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public void sendMimeMessageWithAttachment(String name, String to, String body) {

    }

    @Override
    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String body) {

    }

    @Override
    public void sendMimeMessageWithEmbeddedImages(String name, String to, String body) {

    }

    @Override
    public void sendHtmlEmail(String name, String to, String body) {

    }

    @Override
    public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String body) {

    }
}
