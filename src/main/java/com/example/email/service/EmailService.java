package com.example.email.service;

public interface EmailService {
    void sendSimpleMailMessage(String firstName, String lastName, String to, String body);
    void sendMimeMessageWithAttachment(String firstName, String lastName, String to, String body);
    void sendMimeMessageWithEmbeddedFiles(String firstName, String lastName, String to, String body);
    void sendHtmlEmail(String firstName, String lastName, String to, String body);
    void sendHtmlEmailWithEmbeddedFiles(String firstName, String lastName, String to, String body);
}
