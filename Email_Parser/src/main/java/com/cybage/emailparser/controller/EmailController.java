package com.cybage.emailparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.emailparser.model.EmailData;
import com.cybage.emailparser.service.EmailService;

import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/emails")
    public List<EmailData> getEmails() {
        return emailService.fetchEmails();
    }
}
