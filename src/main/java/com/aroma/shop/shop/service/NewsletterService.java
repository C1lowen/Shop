package com.aroma.shop.shop.service;

import com.aroma.shop.shop.dto.SendAllMessage;
import com.aroma.shop.shop.model.Newsletter;
import com.aroma.shop.shop.repository.NewsletterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class NewsletterService {
    private final NewsletterRepository newsletterRepository;
    private final MailSender mailSender;

    public NewsletterService(NewsletterRepository newsletterRepository, MailSender mailSender) {
        this.newsletterRepository = newsletterRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    public Boolean save(Newsletter newsletter){
        try {
            if(newsletterRepository.findByEmail(newsletter.getEmail())) {
                return false;
            }
            newsletterRepository.save(newsletter);
            return true;
        }catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<String> getListAllEmail() {
        return newsletterRepository.findByAllEmail();
    }

    @Transactional
    public Boolean sendAllEmail(SendAllMessage sendAllMessage) {
        try {
            if(sendAllMessage != null && !sendAllMessage.getSubject().isEmpty()
                    && !sendAllMessage.getMessage().isEmpty()) {
               getListAllEmail()
                        .forEach(email -> mailSender.send(email, sendAllMessage.getSubject(),
                                sendAllMessage.getMessage()));
                return true;
            }else {
                return false;
            }
        } catch (MailSendException e) {
            log.info(e.getMessage());
            throw new MailSendException("Такой почты не существует!");
        }
    }
}
