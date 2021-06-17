package com.api.UDEE.controller;

import com.api.UDEE.domain.Measurement;
import com.api.UDEE.domain.Meter;
import com.api.UDEE.dto.MeasurementsDto;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.MeasurementService;
import com.api.UDEE.service.MeterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.convert.ConversionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/meters")
public class MeterController {

    private MeterService meterService;
    private MeasurementService measurementService;
    private ModelMapper modelMapper;
    private ConversionService conversionService;

    @Autowired
    public MeterController(MeterService meterService,MeasurementService measurementService, ModelMapper modelMapper,ConversionService conversionService){
        this.meterService=meterService;
        this.measurementService= measurementService;
        this.modelMapper=modelMapper;
        this.conversionService= conversionService;
    }

    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity newCountry(@RequestBody Meter meter){
        Meter newMeasure = meterService.newMeter(meter);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMeasure.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping()
    public ResponseEntity<Meter> addReading(@RequestBody MeasurementsDto incoming){
        System.out.println("INCOMING READING: " + incoming.toString());
        Meter existent = meterService.getMeterBySerialNumber(incoming.getSerialNumber());
        if(existent.getPassword().equals(incoming.getPassword())){
            Measurement added = measurementService.add(conversionService.convert(incoming, Measurement.class));
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials.");
        }

        return ResponseEntity.ok(Meter.builder().build());
    }

    @GetMapping("/meters")
    public ResponseEntity<List<Meter>> allMeters(Pageable pageable) {
        Page page = meterService.allMeter(pageable);
        return response(page);
    }

    private ResponseEntity response(Page page) {

        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements()))
                .header("X-Total-Pages", Long.toString(page.getTotalPages()))
                .body(page.getContent());
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<Meter> measureByCode(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Meter measure = meterService.getMeterById(id);
        return ResponseEntity.ok(measure);
    }
}
