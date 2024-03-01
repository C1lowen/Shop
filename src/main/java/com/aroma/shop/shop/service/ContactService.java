package com.aroma.shop.shop.service;

import com.aroma.shop.shop.model.Contact;
import com.aroma.shop.shop.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ContactService {
    private final ContactRepository contactRepository;

    private final MailSender mailSender;

    public ContactService(ContactRepository contactRepository, MailSender mailSender) {
        this.contactRepository = contactRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    public boolean save(Contact contact){
        try {
            contactRepository.save(contact);
            mailSender.sendEmailContact(contact);
            return true;
        }catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

}
