package com.api.UDEE.service;

import com.api.UDEE.domain.Meter;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MeterService {
    private final MeterRepository meterRepository;

    @Autowired
    public MeterService(MeterRepository meterRepository){
        this.meterRepository=meterRepository;
    }

    public Meter getMeterById(Integer id) throws AddressNotExistsException {
        return meterRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public Meter getMeterBySerialNumber(String serialNumber){
        return meterRepository.findBySerialNumber(serialNumber);
    }

    public Meter newMeter(Meter meter){
            return meterRepository.save(meter);
    }
    public Page allMeter(Pageable pageable) {
        return meterRepository.findAll(pageable);
    }
}
