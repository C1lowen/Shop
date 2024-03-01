package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.model.Contact;
import com.aroma.shop.shop.service.ContactService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping ("/save")
    public Boolean saveContact(@RequestBody Contact contact) {
        return contactService.save(contact);
    }
}
