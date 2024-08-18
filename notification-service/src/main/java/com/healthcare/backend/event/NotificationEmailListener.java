package com.healthcare.backend.event;

import com.healthcare.backend.service.EmailService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationEmailListener {

    private final EmailService emailService;

    @KafkaListener(topics = "${notification.email.topic.name}", containerFactory =
            "emailNotificationKafkaListenerContainerFactory")
    public void emailNotificationListener(
            EmailNotification emailNotification) throws MessagingException, TemplateException, IOException {
        log.info("Sending email ...");
        emailService.sendEmailWithSimpleMessage(emailNotification);
        log.info("Send email successfully!");
    }
}