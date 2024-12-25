package com.modsen.passenger.client;

import com.modsen.passenger.dto.RideDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ride-client", url = "${ride.service.url}")
public interface RideClient {

    @GetMapping("/passenger")
    Page<RideDto> getRidesByPassengerId(@RequestParam("passengerId") String passengerId,
                                        @RequestParam("page") int page,
                                        @RequestParam("size") int size);
}