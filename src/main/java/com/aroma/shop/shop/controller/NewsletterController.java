package com.aroma.shop.shop.controller;

import com.aroma.shop.shop.dto.SendAllMessage;
import com.aroma.shop.shop.model.Newsletter;
import com.aroma.shop.shop.service.NewsletterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsletter")
public class NewsletterController {
    private final NewsletterService newsletterService;

    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @PostMapping("/save")
    public Boolean saveNewsletter(@RequestBody Newsletter newsletter){
        return newsletterService.save(newsletter);
    }

    @PostMapping("/send/all")
    public String sendAllEmail(@RequestBody SendAllMessage sendAllMessage) {
        boolean flag = newsletterService.sendAllEmail(sendAllMessage);
        return flag ? "Everything went well!" : "Something went wrong!";
    }
}
