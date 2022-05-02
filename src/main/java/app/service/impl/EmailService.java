package app.service.impl;

import app.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

public class EmailService {

    private User sender;

    public EmailService(User sender) {
        this.sender = sender;
    }

    public void sendMessage(String to, String subject, String content) {
        JavaMailSenderImpl mailSender = createMailSender();
        mailSender.send(createSimpleMessage(to, subject, content));
    }

    private JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(sender.getUsername());
        mailSender.setPassword(sender.getUsername().toUpperCase());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    private SimpleMailMessage createSimpleMessage(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dummypanda31@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        return message;
    }
}
