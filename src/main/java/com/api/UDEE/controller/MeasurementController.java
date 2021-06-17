
package com.api.UDEE.controller;

import java.text.ParseException;
import java.util.Date;

import com.api.UDEE.Utils.EntityURLBuilder;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.convert.ConversionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/measurements")
public class MeasurementController {

    private static final String MEASUREMENT_PATH = "measurement";

    private MeasurementService measurementService;
    private MeterService meterService;
    private ModelMapper modelMapper;
    private ConversionService conversionService;

    @Autowired
    public MeasurementController(MeasurementService measurementService,MeterService meterService,ModelMapper modelMapper, ConversionService conversionService){
        this.measurementService=measurementService;
        this.meterService=meterService;
        this.modelMapper=modelMapper;
        this.conversionService= conversionService;
    }

    @PostMapping("")
    public ResponseEntity add(@RequestBody MeasurementsDto measurement) {
        System.out.println(measurement.toString());
        Meter meter= meterService.getMeterBySerialNumber(measurement.getSerialNumber());

        if(meter != null && meter.getPassword().equals(measurement.getPassword())) {
            Measurement newMeasurement = new Measurement();
            newMeasurement.setDate(measurement.getDate());
            newMeasurement.setMeter(meterService.getMeterBySerialNumber(measurement.getSerialNumber()));
            measurementService.add(newMeasurement);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newMeasurement.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Meter");
        }
    }

    @GetMapping("/measurements")
    public ResponseEntity<List<Measurement>> allMeasurements(Pageable pageable) {
        Page page = measurementService.allMeasurements(pageable);
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
    public ResponseEntity<Measurement> readingByCode(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Measurement reading = measurementService.getMeasurementById(id);
        return ResponseEntity.ok(reading);
    }
}
