package com.modsen.mapper;

import com.modsen.dto.DriverDTO;
import com.modsen.model.Driver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverDTO toDTO(Driver driver);
    Driver toEntity(DriverDTO driverDTO);
}