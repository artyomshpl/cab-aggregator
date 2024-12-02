package com.modsen.passenger.controller;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newPassengerTopic() {
        return TopicBuilder.name("new-passenger")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic passengerUpdatesTopic() {
        return TopicBuilder.name("passenger-updates")
                .partitions(1)
                .replicas(1)
                .build();
    }
}