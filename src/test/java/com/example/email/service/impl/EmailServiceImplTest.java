package com.example.email.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.context.Context;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@Slf4j
@TestPropertySource(locations = "classpath:application.yml")
//@SpringBootTest
class EmailServiceImplTest {
    @Mock
    private JavaMailSender emailSender;
    @Mock
    private TemplateEngine templateEngine;
    @Mock
    MimeMessage mimeMessage;
    @InjectMocks
    private EmailServiceImpl emailService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @BeforeEach
    void setUp() {
        openMocks(this);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        ReflectionTestUtils.setField(emailService, "fromEmail", fromEmail);
        log.info("fromEmail: {}", fromEmail);
//        emailService.setFromEmail(fromEmail);
    }

    @Test
    void sendSimpleMailMessage() {
        doNothing().when(emailSender).send(any(SimpleMailMessage.class));

        emailService.sendSimpleMailMessage(
                "testFirstName", "testLastName", "testEmail", "testMessage");

        verify(emailSender).send(any(SimpleMailMessage.class));

    }

//    @Test
//    void sendMimeMessageWithAttachment() {
//        doNothing().when(emailSender).send(any(MimeMessage.class));
//
//        emailService.sendMimeMessageWithAttachment(
//                "testFirstName", "testLastName", "testEmail", "testMessage");
//
//        verify(emailSender).send(any(MimeMessage.class));
//    }
//
//    @Test
//    void sendMimeMessageWithEmbeddedFiles() {
//        doNothing().when(emailSender).send(any(MimeMessage.class));
//
//        emailService.sendMimeMessageWithEmbeddedFiles(
//                "testFirstName", "testLastName", "testEmail", "testMessage");
//
//        verify(emailSender).send(any(MimeMessage.class));
//    }
//
//    @Test
//    void sendHtmlEmail() throws IOException {
//        when(templateEngine.process(eq("emailtemplate"), any(Context.class))).thenReturn("Mocked HTML Content");
//
//        doNothing().when(emailSender).send(any(MimeMessage.class));
//
//        emailService.sendHtmlEmail(
//                "testFirstName", "testLastName", "testEmail", "testMessage");
//
//        verify(emailSender).send(any(MimeMessage.class));
//        verify(templateEngine).process(eq("emailtemplate"), any(Context.class));
//    }
//
//    @Test
//    void sendHtmlEmailWithEmbeddedFiles() {
//        when(templateEngine.process(eq("emailtemplate"), any(Context.class))).thenReturn("Mocked HTML Content");
//
//        doNothing().when(emailSender).send(any(MimeMessage.class));
//
//        emailService.sendHtmlEmailWithEmbeddedFiles(
//                "testFirstName", "testLastName", "testEmail", "testMessage");
//
//        verify(emailSender).send(any(MimeMessage.class));
//        verify(templateEngine).process(eq("emailtemplate"), any(Context.class));
//    }
}