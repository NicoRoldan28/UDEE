package com.api.UDEE.controller;

import java.util.ArrayList;
import java.util.Date;

import com.api.UDEE.Convertor.MeasurementsDtoToMeasurements;
import com.api.UDEE.domain.Measurement;
import com.api.UDEE.domain.Usuario;
import com.api.UDEE.dto.MeasurementsDto;
import com.api.UDEE.dto.UserDto;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.service.MeasurementService;
import com.api.UDEE.service.MeterService;
import com.api.UDEE.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/measurements")
public class MeasurementController {

    private MeasurementService measurementService;
    private UsuarioService usuarioService;

    private static final String EMPLOYEE = "EMPLOYEE";

    @Autowired
    public MeasurementController(MeasurementService measurementService,UsuarioService usuarioService){
        this.measurementService=measurementService;
        this.usuarioService=usuarioService;
    }

    @GetMapping()
    public ResponseEntity<List<Measurement>> allMeasurements(Pageable pageable,Authentication authentication) {
        if(validateRol(authentication)){
            Page page = measurementService.allMeasurements(pageable);
            return response(page);
        }
        else {
            return (ResponseEntity<List<Measurement>>) ResponseEntity.status(HttpStatus.FORBIDDEN);
        }
    }

    private ResponseEntity response(Page page) {
        System.out.println(page.getContent());
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
    @GetMapping(value = "/dates", produces = "application/json")
    public ResponseEntity<List<MeasurementsDto>> measurementsByDates(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date since,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date until, Authentication authentication, Pageable pageable) throws AddressNotExistsException {
        List<MeasurementsDto> measurementList;
        Page<MeasurementsDto> pages;
        if (!validateRol(authentication)){
            measurementList= measurementService.allMeasurementsByDatesByUser(((UserDto)authentication.getPrincipal()).getId(),since,until,pageable);
        }
        else {
            measurementList= measurementService.allMeasurementsByDates(since,until,pageable);
        }
        pages = new PageImpl<>(measurementList, pageable, measurementList.size());
        return response(pages);
    }

    /*6) Consulta de mediciones de un domicilio por rango de fechas*/
    @GetMapping(value = "/dates/address", produces = "application/json")
    public ResponseEntity<List<MeasurementsDto>> measurementsByDatesByAddress(@RequestParam @PathVariable("idAddress") Integer idAddress,
                                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date since,
                                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date until,
                                                                              Authentication authentication, Pageable pageable) throws AddressNotExistsException {
        List<MeasurementsDto> measurementList=new ArrayList<>();
        Page<MeasurementsDto> pages;
        if (!validateRol(authentication)){
            measurementList= measurementService.allMeasurementsByAddressForDates(idAddress,since,until);
        }
        pages = new PageImpl<>(measurementList, pageable, measurementList.size());
        return response(pages);
    }

    public boolean validateRol(Authentication authentication) throws AddressNotExistsException {
        boolean isEmployee= false;
        Usuario user = usuarioService.getUserById(((UserDto)authentication.getPrincipal()).getId());
        if(user.getTypeUser().getName().equals(EMPLOYEE)){
            isEmployee= true;
        }
        return isEmployee;
    }
}
