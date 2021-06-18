package com.api.UDEE.controller;

import com.api.UDEE.domain.*;
import com.api.UDEE.dto.AddressDto;
import com.api.UDEE.dto.MeasurementsDto;
import com.api.UDEE.dto.MeterDto;
import com.api.UDEE.dto.RatesDto;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Backoffice {

    RateService rateService;
    AddressService addressService;
    MeterService meterService;
    MeasurementService measurementService;
    ModelMapper modelMapper;
    ConversionService conversionService;
    BillService billService;

    @Autowired
    public Backoffice(RateService rateService,AddressService addressService,MeterService meterService,MeasurementService measurementService,ModelMapper modelMapper,ConversionService conversionService,BillService billService ){
        this.rateService=rateService;
        this.addressService=addressService;
        this.meterService=meterService;
        this.measurementService=measurementService;
        this.modelMapper=modelMapper;
        this.conversionService=conversionService;
        this.billService=billService;
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @PostMapping(value = "/api/rates", produces = "application/json")
    public PostResponse newRate(@RequestBody Rate rate) {
        return rateService.newRate(rate);
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @GetMapping(value = "/api/rates", produces = "application/json")
    public ResponseEntity<List<Rate>> allRates(Pageable pageable) {
        Page page = rateService.allRates(pageable);
        return responseRate(page);
    }

    private ResponseEntity responseRate(Page page) {

        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements()))
                .header("X-Total-Pages", Long.toString(page.getTotalPages()))
                .body(page.getContent());
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @GetMapping(value = "/api/rates/{id}", produces = "application/json")
    public ResponseEntity<?> RateByCode(@PathVariable("id") Integer id) throws AddressNotExistsException {
        return new ResponseEntity<>(rateService.getRateById(id), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @DeleteMapping("/api/rates/{id}")
    public ResponseEntity<?> deleteRate(@PathVariable(name = "id") Integer id) {
        return rateService.deleteById(id);
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @PutMapping("/api/rates/{id}")
    public ResponseEntity<?> updateRates(@PathVariable("id") Integer id,@RequestBody RatesDto ratesDto) throws AddressNotExistsException {
        rateService.updateRates(id,ratesDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("")
                .buildAndExpand(id)
                .toUri();
        return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
    }

    /*-------------------------------------------------------------------------------------------------------------*/

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @PostMapping(value = "/api/address", produces = "application/json")
    public ResponseEntity newAddress(@RequestBody Address address){
        Address newAddresss = addressService.newAddress(address);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAddresss.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @GetMapping(value = "/api/address", produces = "application/json")
    public ResponseEntity<List<Address>> allAddress(Pageable pageable) {
        Page page = addressService.allAddress(pageable);
        return responseAddress(page);
    }

    private ResponseEntity responseAddress(Page page) {

        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements()))
                .header("X-Total-Pages", Long.toString(page.getTotalPages()))
                .body(page.getContent());
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @GetMapping(value = "/api/address/{id}", produces = "application/json")
    public ResponseEntity<Address> addressById(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @DeleteMapping("/api/address/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable(name = "id") Integer id) {
        return addressService.deleteById(id);
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @PutMapping("/api/address/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable("id") Integer id,@RequestBody AddressDto addressDto) throws AddressNotExistsException {
        addressService.updateAddress(id,addressDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("")
                .buildAndExpand(id)
                .toUri();
        return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
    }


    /*-------------------------------------------------------------------------------------------------------------*/

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @PostMapping(value = "/api/meter",consumes = "application/json")
    public ResponseEntity newMeter(@RequestBody Meter meter){
        Meter newMeasure = meterService.newMeter(meter);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMeasure.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/measurements")
    public ResponseEntity<Meter> addReading(@RequestBody MeasurementsDto incoming){
        System.out.println("INCOMING READING: " + incoming.toString());
        Meter existent = meterService.getMeterBySerialNumber(incoming.getSerialNumber());
        if(existent.getPassword().equals(incoming.getPassword())){
            Measurement added = measurementService.add(conversionService.convert(incoming,Measurement.class));
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials.");
        }

        return ResponseEntity.ok(Meter.builder().build());
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @GetMapping(value = "/api/meter", produces = "application/json")
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

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @GetMapping(value = "/api/meters/{id}", produces = "application/json")
    public ResponseEntity<Meter> measureByCode(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Meter measure = meterService.getMeterById(id);
        return ResponseEntity.ok(measure);
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @DeleteMapping("/api/meter/{id}")
    public ResponseEntity<?> deleteMeter(@PathVariable(name = "id") Integer id) {
        return meterService.deleteById(id);
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @PutMapping("/api/meter/{id}")
    public ResponseEntity<?> updateMeter(@PathVariable("id") Integer id,@RequestBody MeterDto meterDto) throws AddressNotExistsException {
        meterService.updateMeter(id,meterDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("")
                .buildAndExpand(id)
                .toUri();
        return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
    }

    /*-------------------------------------------------------------------------------------------------------------*/

    @GetMapping(value = "/api/bills/unpaid/{idClient}/address{idAddress}", produces = "application/json")
    public ResponseEntity<List<Bill>> billUnpaid(Authentication authentication,@PathVariable("idClient") Integer idClient,@PathVariable("idAddress") Integer idAddress) throws AddressNotExistsException {
        List<Bill> listBill = new ArrayList<Bill>();
        listBill= billService.allBillUnpaidByUserAndAddress(idClient,idAddress);
        return (ResponseEntity<List<Bill>>) listBill;
    }

}
