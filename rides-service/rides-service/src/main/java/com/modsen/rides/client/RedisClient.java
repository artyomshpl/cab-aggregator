package com.modsen.rides.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "redis-client", url = "${redis.service.url}")
public interface RedisClient {

    @GetMapping("/get")
    String getValue(@RequestParam("key") String key);
}
