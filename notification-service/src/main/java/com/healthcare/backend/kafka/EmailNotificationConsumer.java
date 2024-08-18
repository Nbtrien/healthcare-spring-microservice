package com.healthcare.backend.kafka;

import com.healthcare.backend.event.EmailNotification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EmailNotificationConsumer {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value(value = "${kafka.group-id}")
    private String groupId;

    public ConsumerFactory<String, EmailNotification> emailNotificationConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                                                 new JsonDeserializer<>(EmailNotification.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailNotification> emailNotificationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailNotification> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailNotificationConsumerFactory());
        return factory;
    }
}