package com.example.email.service.impl;

import com.example.email.service.EmailService;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.sql.DataSource;
import java.io.File;
import java.util.Map;

import static com.example.email.utils.EmailUtils.getEmailMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

//    public static final String EMAIL_TEMPLATE = "emailtemplate";
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    public static final String SUBJECT_THE_MANOR = "New tennis coach at The Manor Golf and Country Club";
    private static final String UTF_8_ENCODING = "UTF-8";
    @Value("${spring.mail.verify.host")
    private String host;
    @Value("${spring.mail.username")
    private String fromEmail;


    @Override
    @Async
    public void sendSimpleMailMessage(String firstName, String lastName, String to, String body) {
        String name = firstName + " " + lastName;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(SUBJECT_THE_MANOR);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(getEmailMessage(firstName, lastName, host));
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithAttachment(String firstName, String lastName, String to, String body) {
        String name = firstName + " " + lastName;
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(SUBJECT_THE_MANOR);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(getEmailMessage(firstName, lastName, host));
            //Add attachment
//            FileSystemResource tennis = new FileSystemResource(new File("src/main/resources/static/images/tennis.jpg"));
            FileSystemResource wenGirls = new FileSystemResource(new File("C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\WenGirls.jpg"));
            FileSystemResource dogs = new FileSystemResource(new File("C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\Dogs.jpg"));
//            FileSystemResource dogs = new FileSystemResource(new File(System.getProperty("user.home") + "/Pictures/Dgs.jpg"));
//            FileSystemResource wenGirls = new FileSystemResource(new File(System.getProperty("user.home") + "/Pictures/WenGirls.jpg"));
//            helper.addAttachment(tennis.getFilename(), tennis);
            helper.addAttachment(dogs.getFilename(), dogs);
            helper.addAttachment(wenGirls.getFilename(), wenGirls);
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }



    @Override
    @Async
    public void sendMimeMessageWithEmbeddedFiles(String firstName, String lastName, String to, String body) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(SUBJECT_THE_MANOR);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(getEmailMessage(firstName, lastName, host));
            //Add attachment
            FileSystemResource wenGirls = new FileSystemResource(new File("C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\WenGirls.jpg"));
            FileSystemResource dogs = new FileSystemResource(new File("C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\Dogs.jpg"));
            helper.addInline(getContentId(dogs.getFilename()), dogs);
            helper.addInline(getContentId(wenGirls.getFilename()), wenGirls);
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendHtmlEmail(String firstName, String lastName, String to, String body) {
        String name = firstName + " " + lastName + ",";
        try {
            Context context = new Context();
//            context.setVariable("firstName", firstName);
//            context.setVariable("lastName,\n", lastName);
//            context.setVariables(Map.of("firstName", firstName, "lastName", lastName));
            context.setVariables(Map.of("name", name));
            String text = templateEngine.process("emailtemplate", context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(SUBJECT_THE_MANOR);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendHtmlEmailWithEmbeddedFiles(String firstName, String lastName, String to, String body) {
        String name = firstName + " " + lastName + ",";
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(SUBJECT_THE_MANOR);
            helper.setFrom(fromEmail);
            helper.setTo(to);

            Context context = new Context();
            context.setVariables(Map.of("name", name));
            String text = templateEngine.process("emailtemplate", context);

            //Add HTML email body
//            MimeMultipart mimeMultipart = new MimeMultipart("related");
//            BodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setContent(text, "text/html");
//            mimeMultipart.addBodyPart(messageBodyPart);
//
//            //Add images to the email body
//            BodyPart imageBodyPart = new MimeBodyPart();
//            DataSource dataSource = new FileDataSource("C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\Dogs.jpg");
//            imageBodyPart.setDataHandler(new DataHandler(dataSource));
//            imageBodyPart.setHeader("Content-ID", "image");
//            mimeMultipart.addBodyPart(imageBodyPart);

            //alt version
            helper.setText(text, true);

            String imagePath = "C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\Dogs.jpg";
            FileSystemResource file = new FileSystemResource(new File(imagePath));
            if(!file.exists()) {
                throw new RuntimeException("File not found at path: " + imagePath);
            }
            helper.addInline("image", file, "image/jpeg");

//            message.setContent(mimeMultipart);
            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
    private String getContentId(String filename) {
        return "<" + filename + ">";
    }
}
