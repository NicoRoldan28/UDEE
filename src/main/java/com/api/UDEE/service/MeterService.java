package com.api.UDEE.service;

import com.api.UDEE.Convertor.MeterToMeterDto;
import com.api.UDEE.domain.Meter;
import com.api.UDEE.dto.MeterDto;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.exceptions.notFound.MeterNotExistsException;
import com.api.UDEE.repository.MeterRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeterService {
    private final MeterRepository meterRepository;
    private MeterToMeterDto meterToMeterDto;

    @Autowired
    public MeterService(MeterRepository meterRepository,MeterToMeterDto meterToMeterDto){
        this.meterRepository=meterRepository;
        this.meterToMeterDto=meterToMeterDto;
    }

    public Meter getMeterById(Integer id) throws MeterNotExistsException {
        return meterRepository.findById(id).orElseThrow(()-> new MeterNotExistsException("No Meter was found by that id"));
    }

    public Meter getMeterBySerialNumber(String serialNumber){
        return meterRepository.findBySerialNumber(serialNumber);
    }

    public Meter newMeter(Meter meter){

        return meterRepository.save(meter);
    }

    public Page allMeter(Pageable pageable)
    {
        Page<Meter> meters=meterRepository.findAll(pageable);
        Page<MeterDto> meterDtos =meterToMeterDto.convertPageToDto(meters);
        return meterDtos;
    }

    public ResponseEntity<?> deleteById(Integer id) {
            if (this.meterRepository.findById(id) != null){
                meterRepository.deleteById(id);
                return new ResponseEntity<>("Se ha eliminado el medidor con éxito.", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    public void updateMeter(Integer id, MeterDto meterDto) throws AddressNotExistsException {
        Meter meter=this.getMeterById(id);
        List<MeterDto> list=new ArrayList<MeterDto>();
        list.add(meterDto);

        for ( MeterDto a: list){
            if (a.getSerialNumber()!=null) {
                meter.setSerialNumber(meterDto.getSerialNumber());
            }
            if (a.getAddress()!=null){
                //meter.setAddress(meterDto.getAddress());
            }
        }
        meterRepository.save((meter));
    }
}
