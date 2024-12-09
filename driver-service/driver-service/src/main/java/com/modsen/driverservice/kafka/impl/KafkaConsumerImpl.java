package com.modsen.driverservice.kafka.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.driverservice.dto.DriverDTO;
import com.modsen.driverservice.exception.KafkaMessageProcessingException;
import com.modsen.driverservice.kafka.KafkaConsumer;
import com.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer {
    private final DriverService driverService;
    private final KafkaTemplate<String, List<DriverDTO>> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "need-free-drivers")
    public void listenForNeedFreeDrivers(JsonNode message) {
        try {
            List<DriverDTO> freeDrivers = driverService.getFreeDrivers();
            kafkaTemplate.send("send-free-drivers", freeDrivers);
        } catch (Exception e) {
            throw new KafkaMessageProcessingException("Error processing need-free-drivers message", e);
        }
    }

    @Override
    @KafkaListener(topics = "driver-updates")
    public void listenForDriverUpdates(JsonNode message) {
        try {
            DriverDTO driverDTO = objectMapper.treeToValue(message, DriverDTO.class);
            driverService.updateDriver(driverDTO);
        } catch (Exception e) {
            throw new KafkaMessageProcessingException("Error processing driver-updates message", e);
        }
    }
}
