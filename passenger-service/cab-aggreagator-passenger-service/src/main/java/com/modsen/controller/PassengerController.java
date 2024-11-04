package com.modsen.controller;

import com.modsen.dto.PassengerDTO;
import com.modsen.exception.ResourceNotFoundException;
import com.modsen.mapper.PassengerMapper;
import com.modsen.model.Passenger;
import com.modsen.service.impl.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private PassengerMapper passengerMapper;

    @PostMapping("/save")
    public PassengerDTO savePassenger(@RequestBody PassengerDTO passengerDto) {
        Passenger passenger = passengerMapper.toEntity(passengerDto);
        Passenger savedPassenger = passengerService.savePassenger(passenger);
        return passengerMapper.toDto(savedPassenger);
    }

    @GetMapping("/all")
    public List<PassengerDTO> getAllPassengers() {
        return passengerService.getAllPassengers().stream()
                .map(passengerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/name")
    public PassengerDTO getPassengerByName(@RequestParam("name") String name) {
        Passenger passenger = passengerService.getPassengerByName(name);
        if (passenger != null) {
            return passengerMapper.toDto(passenger);
        } else {
            throw new ResourceNotFoundException("Passenger not found with name: " + name);
        }
    }

    @GetMapping("/{id}")
    public PassengerDTO getPassengerById(@PathVariable Long id) {
        Passenger passenger = passengerService.getPassengerById(id);
        if (passenger != null) {
            return passengerMapper.toDto(passenger);
        } else {
            throw new ResourceNotFoundException("Passenger not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
    }
}
