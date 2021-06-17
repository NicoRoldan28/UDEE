package com.api.UDEE.repository;

import com.api.UDEE.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeterRepository extends JpaRepository<Meter,Integer> {
    Meter findBySerialNumber(String serialNumber);
}
