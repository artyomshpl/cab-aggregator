package com.modsen.rides.kafka;

import com.fasterxml.jackson.databind.JsonNode;

public interface KafkaConsumer {
    void listenSendFreeDrivers(JsonNode message);
    void listenNewPassenger(JsonNode message);
}
