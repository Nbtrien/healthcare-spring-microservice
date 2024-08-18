package com.healthcare.backend.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationProducer {
    private final KafkaTemplate<String, EmailNotification> kafkaTemplate;

    @Value(value = "${notification.email.topic}")
    private String emailNotificationTopic;

    public void sendEmailNotificationMessage(EmailNotification emailNotification) {
        kafkaTemplate.send(emailNotificationTopic, emailNotification);
    }
}