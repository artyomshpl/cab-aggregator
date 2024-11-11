package com.modsen.driverservice.mapper;

import com.modsen.driverservice.model.Driver;
import com.modsen.driverservice.dto.DriverDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverDTO toDTO(Driver driver);
    Driver toEntity(DriverDTO driverDTO);
}