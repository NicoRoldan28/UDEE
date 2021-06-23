package com.api.UDEE.controller;

import java.util.ArrayList;
import java.util.Date;

import com.api.UDEE.domain.Measurement;
import com.api.UDEE.domain.Usuario;
import com.api.UDEE.dto.UserDto;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.service.MeasurementService;
import com.api.UDEE.service.MeterService;
import com.api.UDEE.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.convert.ConversionService;

import java.util.List;

@RestController
@RequestMapping(value = "/measurements")
public class MeasurementController {

    private MeasurementService measurementService;
    private MeterService meterService;
    private ModelMapper modelMapper;
    private ConversionService conversionService;
    private UsuarioService usuarioService;

    private static final String CLIENT = "CLIENT";

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeterService meterService, ModelMapper modelMapper, ConversionService conversionService, UsuarioService usuarioService){
        this.measurementService=measurementService;
        this.meterService=meterService;
        this.modelMapper=modelMapper;
        this.conversionService= conversionService;
        this.usuarioService=usuarioService;
    }

    @GetMapping()
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

    /*5) Consulta de mediciones por rango de fechas*/
    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @GetMapping(value = "/dates", produces = "application/json")
    public ResponseEntity<List<Measurement>> measurementsByDates(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Authentication authentication) throws AddressNotExistsException {
        List<Measurement> listMeasurements = new ArrayList<Measurement>();
        if (validateRol(authentication)){
            listMeasurements= measurementService.allMeasurementsByDates(from,to);
            return (ResponseEntity<List<Measurement>>) listMeasurements;
        }
        else {
            return (ResponseEntity<List<Measurement>>) ResponseEntity.status(HttpStatus.FORBIDDEN);
        }
    }

    public boolean validateRol(Authentication authentication) throws AddressNotExistsException {
        boolean isUser= false;
        Usuario user = usuarioService.getUserById(((UserDto)authentication.getPrincipal()).getId());
        if(user.getTypeUser().getName().equals(CLIENT)){
            isUser= true;
        }
        return isUser;
    }
}
