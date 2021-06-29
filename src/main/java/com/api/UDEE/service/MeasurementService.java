package com.api.UDEE.service;

import com.api.UDEE.Convertor.MeasurementsDtoToMeasurements;
import com.api.UDEE.domain.Measurement;
import com.api.UDEE.dto.MeasurementsDto;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    @Autowired
    MeasurementsDtoToMeasurements measurementsDtoToMeasurements;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository){
        this.measurementRepository=measurementRepository;
    }

    public Measurement getMeasurementById(Integer id) throws AddressNotExistsException {
        return measurementRepository.findById(id).orElseThrow(()-> new AddressNotExistsException("No Measurement was found by that id"));
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

    public List<MeasurementsDto> allMeasurementsByDates(Date from, Date to, Pageable pageable){
        List<Measurement>measurementList=  measurementRepository.findByMeasurementBetweenDates(from,to);

        List<MeasurementsDto>measurementsDtos= new ArrayList<MeasurementsDto>();

        for (int i=0;i<measurementList.size();i++){
            measurementsDtos.add(measurementsDtoToMeasurements.convertToDto(measurementList.get(i)));
        }

        return measurementsDtos;
    }

    public List<MeasurementsDto> allMeasurementsByDatesByUser(int id, Date from, Date to,Pageable pageable){

        List<Measurement>measurementList= measurementRepository.findByMeasurementsBetweenDateByUser(id,from,to,pageable);
        List<MeasurementsDto>measurementsDtos= cast(measurementList);
        return measurementsDtos;
    }

    public List<MeasurementsDto> allMeasurementsByAddressForDates(Integer idAddress,Date from,Date to){
        List<Measurement>measurementList= measurementRepository.measurementsByDates(idAddress,from,to);
        List<MeasurementsDto>measurementsDtos= cast(measurementList);
        return measurementsDtos;
    }

    public List <MeasurementsDto> cast(List<Measurement> measurementList){
        List<MeasurementsDto>measurementsDtos= new ArrayList<MeasurementsDto>();
        for (int i=0;i<measurementList.size();i++){
            measurementsDtos.add(measurementsDtoToMeasurements.convertToDto(measurementList.get(i)));
        }

        return measurementsDtos;
    }
}
