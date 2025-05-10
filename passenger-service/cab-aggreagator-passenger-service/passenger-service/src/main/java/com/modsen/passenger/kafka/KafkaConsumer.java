package com.modsen.passenger.kafka;

import com.fasterxml.jackson.databind.JsonNode;

public interface KafkaConsumer {
    void listenPassengerUpdates(JsonNode passenger);
}