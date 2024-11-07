package com.modsen.driverservice.service.impl;

import com.modsen.driverservice.dto.DriverDTO;
import com.modsen.driverservice.exception.DriverNotFoundException;
import com.modsen.driverservice.mapper.DriverMapper;
import com.modsen.driverservice.model.Driver;
import com.modsen.driverservice.repository.DriverRepository;
import com.modsen.driverservice.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<DriverDTO> getAllDrivers(Pageable pageable) {
        return driverRepository.findAll(pageable).map(driverMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverDTO getDriverById(String id) {
        return driverRepository.findById(id)
                .map(driverMapper::toDTO)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found with id: " + id));
    }


    @Override
    @Transactional
    public DriverDTO saveDriver(DriverDTO driverDTO) {
        Driver driver = driverMapper.toEntity(driverDTO);
        return driverMapper.toDTO(driverRepository.save(driver));
    }

    @Override
    @Transactional
    public void deleteDriver(String id) {
        if (!driverRepository.existsById(id)) {
            throw new DriverNotFoundException("Driver not found with id: " + id);
        }
        driverRepository.deleteById(id);
    }
}
