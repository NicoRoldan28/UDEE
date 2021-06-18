package com.api.UDEE.service;

import com.api.UDEE.domain.Bill;
import com.api.UDEE.domain.Measurement;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
            return measurementRepository.save(reading);
    }

    public Page allMeasurements(Pageable pageable) {
        return measurementRepository.findAll(pageable);
    }

    public  Measurement add(Measurement measurement){
        return measurementRepository.save(measurement);
    }

    public List<Measurement> allMeasurementsByDates(Date from, Date to){
        return measurementRepository.findAll();
    }
}
