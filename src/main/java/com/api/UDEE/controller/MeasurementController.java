package com.api.UDEE.controller;

import com.api.UDEE.domain.Client;
import com.api.UDEE.domain.Measurement;
import com.api.UDEE.domain.User;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.MeasurementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/measurements")
public class MeasurementController {

    private MeasurementService measurementService;
    private ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper){
        this.measurementService=measurementService;
        this.modelMapper=modelMapper;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity newReading(@RequestBody Measurement reading){
        Measurement newReading = measurementService.newMeasurement(reading);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newReading.getId())
                .toUri();
        return ResponseEntity.created(location).build();
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
