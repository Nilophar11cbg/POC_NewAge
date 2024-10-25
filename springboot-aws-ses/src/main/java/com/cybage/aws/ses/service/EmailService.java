package com.cybage.aws.ses.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;

@Service
public class EmailService {

    @Autowired
    private AmazonSimpleEmailService simpleEmailService;

    public void sendTemplatedMessage(SimpleMailMessage simpleMailMessage) {

        Destination destination = new Destination();
        List<String> toAddresses = new ArrayList<>();
        String[] emails = simpleMailMessage.getTo();
        Collections.addAll(toAddresses, Objects.requireNonNull(emails));
        destination.setToAddresses(toAddresses);

        SendTemplatedEmailRequest templatedEmailRequest = new SendTemplatedEmailRequest();
        templatedEmailRequest.withDestination(destination);
        templatedEmailRequest.withTemplate("MyTemplate");
        templatedEmailRequest.withTemplateData("{ \"name\":\"Cybage\", \"location\": \"Pune, India\"}");
        //templatedEmailRequest.withTemplateData("{ \"name\":\"Cybage\"}");
        templatedEmailRequest.withSource(simpleMailMessage.getFrom());
        System.out.println("Email sent to: " + destination);
        simpleEmailService.sendTemplatedEmail(templatedEmailRequest);
    }
}