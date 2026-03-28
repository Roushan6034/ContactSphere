package org.majorproject.contactsphere.Services;

import jakarta.validation.constraints.Email;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}