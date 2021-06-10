package com.api.UDEE.service;

import com.api.UDEE.domain.Meter;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.exceptions.CountryExistsException;
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

    public Meter newMeter(Meter meter) throws CountryExistsException {
        if (!meterRepository.existsById(meter.getId())) {
            return meterRepository.save(meter);
        }
        else{
                throw new CountryExistsException();
        }
    }
    public Page allMeter(Pageable pageable) {
        return meterRepository.findAll(pageable);
    }
}
