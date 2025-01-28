package com.modsen.notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic driverRequestRideTopic() {
        return TopicBuilder.name("driver-request-ride")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic startRideConfirmationTopic() {
        return TopicBuilder.name("start-ride-confirmation")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic endRideConfirmationTopic() {
        return TopicBuilder.name("end-ride-confirmation")
                .partitions(1)
                .replicas(1)
                .build();
    }
}

