package com.api.UDEE.service;

import com.api.UDEE.domain.Measurement;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository){
        this.measurementRepository=measurementRepository;
    }

    public Measurement getMeasurementById(Integer id) throws AddressNotExistsException {
        return measurementRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public Measurement newMeasurement(Measurement reading) {
        if (!measurementRepository.existsById(reading.getId())) {
            return measurementRepository.save(reading);
        }
        else{
            return null;
        }
    }

    public Page allMeasurements(Pageable pageable) {
        return measurementRepository.findAll(pageable);
    }

}
