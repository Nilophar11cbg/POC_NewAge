package com.cybage.emailparser.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cybage.emailparser.model.EmailData;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private String port;

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    @Value("${email.protocol}")
    private String protocol;
    
    public List<EmailData> fetchEmails() {
        List<EmailData> emails = new ArrayList<>();
        try {
            // Set properties for mail session
            Properties props = new Properties();
            props.put("mail.store.protocol", protocol);
            props.put("mail.imap.host", host);
            props.put("mail.imap.port", port);
            props.put("mail.imap.ssl.enable", "true");
            props.put("mail.imap.starttls.enable", "true");

            // Create session
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore(protocol);
            store.connect(host, username, password);
            
            // Access inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Fetch emails
            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                EmailData emailData = new EmailData();
                emailData.setSubject(message.getSubject());
                emailData.setSender(((InternetAddress) message.getFrom()[0]).getAddress());
                emailData.setContent(getTextFromMessage(message));
                emails.add(emailData);
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emails;
    }

    private String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("text/html")) {
            return message.getContent().toString();
        } else if (message.getContent() instanceof MimeMultipart) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                result.append(bodyPart.getContent());
            }
        }
        return result.toString();
    }
  
}
