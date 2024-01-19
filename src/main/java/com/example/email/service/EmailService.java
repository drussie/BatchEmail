package com.example.email.service;

public interface EmailService {
    void sendSimpleMailMessage(String name, String to, String body);
    void sendMimeMessageWithAttachment(String name, String to, String body);
    void sendMimeMessageWithEmbeddedFiles(String name, String to, String body);
    void sendMimeMessageWithEmbeddedImages(String name, String to, String body);
    void sendHtmlEmail(String name, String to, String body);
    void sendHtmlEmailWithEmbeddedFiles(String name, String to, String body);
}
