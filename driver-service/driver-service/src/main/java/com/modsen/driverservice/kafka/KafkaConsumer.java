package com.modsen.driverservice.kafka;

import com.fasterxml.jackson.databind.JsonNode;

public interface KafkaConsumer {
    void listenForNeedFreeDrivers(JsonNode message);
    void listenForDriverUpdates(JsonNode message);
}
