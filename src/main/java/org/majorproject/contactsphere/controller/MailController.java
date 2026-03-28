package org.majorproject.contactsphere.controller;

import org.majorproject.contactsphere.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.Socket;

@RestController
public class MailController {
    @Autowired
    private EmailService emailService;
    @GetMapping("/send")
    public String sendMail() {
        emailService.sendEmail(
                "learncodewithdurgesh@gmail.com",
                "just testing mail",
                "This is a test mail"
        );
        return "Mail Sent!";
    }

}
