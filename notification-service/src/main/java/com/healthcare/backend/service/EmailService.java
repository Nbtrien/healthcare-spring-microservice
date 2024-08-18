package com.healthcare.backend.service;

import com.healthcare.backend.event.EmailNotification;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {
    void sendEmailWithSimpleMessage(
            EmailNotification emailNotification) throws MessagingException, TemplateException, IOException;
}
