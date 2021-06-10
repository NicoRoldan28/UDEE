package com.api.UDEE.controller;

import com.api.UDEE.domain.Meter;
import com.api.UDEE.domain.User;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.exceptions.CountryExistsException;
import com.api.UDEE.service.MeterService;
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
@RequestMapping(value = "/api/meters")
public class MeterController {

    private MeterService meterService;
    private ModelMapper modelMapper;

    @Autowired
    public MeterController(MeterService meterService, ModelMapper modelMapper){
        this.meterService=meterService;
        this.modelMapper=modelMapper;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity newCountry(@RequestBody Meter meter)  throws CountryExistsException {
        Meter newMeasure = meterService.newMeter(meter);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMeasure.getId())
                .toUri();
        return ResponseEntity.created(location).build();
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
