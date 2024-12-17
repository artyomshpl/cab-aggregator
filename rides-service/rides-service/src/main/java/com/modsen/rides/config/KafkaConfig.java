package com.modsen.rides.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic needFreeDriversTopic() {
        return TopicBuilder.name("need-free-drivers")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sendFreeDriversTopic() {
        return TopicBuilder.name("send-free-drivers")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic newRideRequestTopic() {
        return TopicBuilder.name("new-passenger")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic driverUpdatesTopic() {
        return TopicBuilder.name("driver-updates")
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

    @Bean
    public NewTopic requestRidesTopic() {
        return TopicBuilder.name("request-rides")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic updateRideRatingTopic() {
        return TopicBuilder.name("update-ride-rating")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sendRidesTopic() {
        return TopicBuilder.name("send-rides")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
