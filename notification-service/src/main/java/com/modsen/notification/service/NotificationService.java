package com.modsen.notification.service;

import com.modsen.notification.dto.RideDto;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public NotificationService() {
        scheduler.scheduleAtFixedRate(this::sendKeepAliveMessages, 30, 30, TimeUnit.SECONDS);
    }

    private void sendKeepAliveMessages() {
        emitters.forEach((driverId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().name("keep-alive").data("keep-alive"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void notifyDriver(RideDto rideDto, String eventType) {
        SseEmitter emitter = emitters.get(rideDto.driverId());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(eventType).data(rideDto));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public SseEmitter subscribe(String driverId) {
        SseEmitter emitter = new SseEmitter(60 * 60 * 1000L);
        emitters.put(driverId, emitter);
        emitter.onCompletion(() -> emitters.remove(driverId));
        emitter.onTimeout(() -> emitters.remove(driverId));
        return emitter;
    }
}

