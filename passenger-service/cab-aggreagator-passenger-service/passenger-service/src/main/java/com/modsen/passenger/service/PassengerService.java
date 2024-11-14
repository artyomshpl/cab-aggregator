package com.modsen.passenger.service;

import com.modsen.passenger.dto.PassengerRequestDto;
import com.modsen.passenger.dto.PassengerResponseDto;
import com.modsen.passenger.dto.PageResponseDto;
import com.modsen.passenger.dto.PassengerListResponseDto;

public interface PassengerService {
    PassengerResponseDto savePassenger(PassengerRequestDto passengerRequestDto);
    PageResponseDto<PassengerResponseDto> getAllPassengers(int page, int size);
    PassengerResponseDto getPassengerById(Long id);
    PassengerListResponseDto getPassengersByName(String name);
    void deletePassenger(Long id);
}
