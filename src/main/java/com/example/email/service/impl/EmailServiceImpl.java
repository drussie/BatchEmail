package com.example.email.service.impl;

import com.example.email.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import static com.example.email.utils.EmailUtils.getEmailMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    public static final String SUBJECT_THE_MANOR = "Enhance Your Tennis Skills with Marcos Ondruska at The Manor Golf and Country Club";
    private static final String UTF_8_ENCODING = "UTF-8";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
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
            FileSystemResource wenGirls = new FileSystemResource(new File("C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\WenGirls.jpg"));
            FileSystemResource dogs = new FileSystemResource(new File("C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\Dogs.jpg"));

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

            helper.setText(text, true);

            //Add image 1
//            String imagePath = "C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\dogs2.jpg";
//            FileSystemResource file = new FileSystemResource(new File(imagePath));
//            if(!file.exists()) {
//                throw new RuntimeException("File not found at path: " + imagePath);
//            }
//            helper.addInline("image", file, "image/jpeg");

            //Add image 2
            String imagePath2 = "C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\marcosMichaelChang.jpg";
            FileSystemResource file2 = new FileSystemResource(new File(imagePath2));
            if(!file2.exists()) {
                throw new RuntimeException("File not found at path: " + imagePath2);
            }
            helper.addInline("image2", file2, "image/jpeg");

            //Add image 3
            String imagePath3 = "C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\familyPicWinter.jpg";
            FileSystemResource file3 = new FileSystemResource(new File(imagePath3));
            if(!file3.exists()) {
                throw new RuntimeException("File not found at path: " + imagePath3);
            }
            helper.addInline("image3", file3, "image/jpeg");

            //Add image 4
            String imagePath4 = "C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\marcosTeaching.jpg";
            FileSystemResource file4 = new FileSystemResource(new File(imagePath4));
            if(!file4.exists()) {
                throw new RuntimeException("File not found at path: " + imagePath4);
            }
            helper.addInline("image4", file4, "image/jpeg");

            //Add image 5
            String imagePath5 = "C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\marcosDc2.jpg";
            FileSystemResource file5 = new FileSystemResource(new File(imagePath5));
            if(!file5.exists()) {
                throw new RuntimeException("File not found at path: " + imagePath5);
            }
            helper.addInline("image5", file5, "image/jpeg");

            //Add image 6
            String imagePath6 = "C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\family.jpg";
            FileSystemResource file6 = new FileSystemResource(new File(imagePath6));
            if(!file6.exists()) {
                throw new RuntimeException("File not found at path: " + imagePath6);
            }
            helper.addInline("image6", file6, "image/jpeg");

            //Add image 7
            String imagePath7 = "C:\\Users\\marco\\OneDrive\\Pictures\\Billy Wen Pics\\helmets.jpg";
            FileSystemResource file7 = new FileSystemResource(new File(imagePath7));
            if(!file7.exists()) {
                throw new RuntimeException("File not found at path: " + imagePath7);
            }
            helper.addInline("image7", file7, "image/jpeg");

            long emailSize = calculateEmailSize(message);
            System.out.println("Email size: " + emailSize + " bytes");

            if (emailSize > 5242880 && host.equals("smtp.mailtrap.io")) {
                throw new RuntimeException("Email size exceeds maximum size for Mailtrap");
            }

            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            log.info(exception.getMessage() + " with client:" + firstName + " " + lastName + " with email: " + to);
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    private long calculateEmailSize(MimeMessage message) throws MessagingException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        byte[] messageBytes = outputStream.toByteArray();
        return messageBytes.length;
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
    private String getContentId(String filename) {
        return "<" + filename + ">";
    }
}
