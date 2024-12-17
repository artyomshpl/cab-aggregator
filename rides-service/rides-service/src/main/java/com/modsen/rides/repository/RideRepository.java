package com.modsen.rides.repository;


import com.modsen.rides.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    Ride findTopByOrderByIdDesc();
    Page<Ride> findAll(Pageable pageable);
    List<Ride> findByPassengerId(String id);
}