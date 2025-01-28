package com.modsen.notification.kafka;

import com.fasterxml.jackson.databind.JsonNode;

public interface KafkaConsumer {
    void listenDriverRequestRide(JsonNode message);
    void listenStartRideConfirmation(JsonNode message);
    void listenEndRideConfirmation(JsonNode message);
}
