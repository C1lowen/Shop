package com.aroma.shop.shop.service;

import com.aroma.shop.shop.dto.SendAllMessage;
import com.aroma.shop.shop.model.Contact;
import com.aroma.shop.shop.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
@Slf4j
public class MailSender {
    private final JavaMailSender mailSender;
    private final UserService userService;

    @Value("${spring.mail.username}")
    private String name;

    @Value("${url.link}")
    private String rootUrl;

    public MailSender(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(name);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
    @Transactional
    public String sendEmailActivator(String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if(userService.findUserByEmail(email).isEmpty()) return "false";
        String activationCode = UUID.randomUUID().toString();
        User userResult = user.get();
        userResult.setActivateCode(activationCode);

        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Aroma shop! Activation link: "+ rootUrl+"%s",
                user.get().getUsername(), activationCode);
        send(email, "Activation code", message);

        return "true";
    }

    @Transactional
    public void sendEmailContact(Contact contact) throws MailSendException{
        try {
            String message = String.format(
                    """
                            Hello, %s!\s
                            Your request has been accepted. Our staff will contact you shortly. Thanks for reaching out!

                            Your order number: %d""",
                    contact.getName(), contact.getId());
            send(contact.getEmail(), "Support Aroma Shop", message);
        } catch (MailSendException e) {
            log.info(e.getMessage());
            throw new MailSendException("Такой почты не существует!");
        }
    }

}
