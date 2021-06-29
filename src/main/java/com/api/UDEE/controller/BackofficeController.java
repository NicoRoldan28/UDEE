package com.api.UDEE.controller;

import com.api.UDEE.domain.*;
import com.api.UDEE.dto.*;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.exceptions.notFound.MeterNotExistsException;
import com.api.UDEE.exceptions.notFound.RateNotExistsException;
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
import java.util.List;

@RestController
public class BackofficeController {

    RateService rateService;
    AddressService addressService;
    MeterService meterService;
    MeasurementService measurementService;
    ModelMapper modelMapper;
    ConversionService conversionService;
    BillService billService;
    UsuarioService usuarioService;

    private static final String CLIENT = "CLIENT";

    @Autowired
    public BackofficeController(RateService rateService, AddressService addressService, MeterService meterService, MeasurementService measurementService, ModelMapper modelMapper, ConversionService conversionService, BillService billService, UsuarioService usuarioService ){
        this.rateService=rateService;
        this.addressService=addressService;
        this.meterService=meterService;
        this.measurementService=measurementService;
        this.modelMapper=modelMapper;
        this.conversionService=conversionService;
        this.billService=billService;
        this.usuarioService=usuarioService;
    }

    @PostMapping(value = "/api/rates", produces = "application/json")
    public ResponseEntity newRate(@RequestBody Rate rate, Authentication authentication) {
        if (!validateRol(authentication)) {
            Rate newRate= rateService.newRate(rate);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newRate.getId())
                    .toUri();
            return new ResponseEntity<>(location,(HttpStatus.CREATED));
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/api/rates", produces = "application/json")
    public ResponseEntity<List<Rate>> allRates(Pageable pageable, Authentication authentication) {
        if (!validateRol(authentication)) {
            Page page = rateService.allRates(pageable);
            return responseRate(page);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private ResponseEntity responseRate(Page page) {

        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements()))
                .header("X-Total-Pages", Long.toString(page.getTotalPages()))
                .body(page.getContent());
    }

    @GetMapping(value = "/api/rates/{id}", produces = "application/json")
    public ResponseEntity<?> RateByCode(@PathVariable("id") Integer id, Authentication authentication) throws RateNotExistsException {
        if (!validateRol(authentication)) {
            return new ResponseEntity<>(rateService.getRateById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/api/rates/{id}")
    public ResponseEntity<?> deleteRate(@PathVariable(name = "id") Integer id, Authentication authentication) {
        if(!validateRol(authentication)){
            return rateService.deleteById(id);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/api/rates/{id}")
    public ResponseEntity<?> updateRates(@PathVariable("id") Integer id,@RequestBody RatesDto ratesDto, Authentication authentication) throws RateNotExistsException {
        if(!validateRol(authentication)){
            rateService.updateRates(id,ratesDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("")
                    .buildAndExpand(id)
                    .toUri();
            return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /*-------------------------------------------------------------------------------------------------------------*/

    @PostMapping(value = "/api/address", produces = "application/json")
    public ResponseEntity newAddress(@RequestBody Address address, Authentication authentication){
        if(!validateRol(authentication)){
        Address newAddresss = addressService.newAddress(address);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAddresss.getId())
                .toUri();
            return new ResponseEntity<>(location,(HttpStatus.CREATED));
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/api/address", produces = "application/json")
    public ResponseEntity<List<Address>> allAddress(Pageable pageable, Authentication authentication) {
        if(!validateRol(authentication)){
            Page page = addressService.allAddress(pageable);
            return responseAddress(page);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private ResponseEntity responseAddress(Page page) {

        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements()))
                .header("X-Total-Pages", Long.toString(page.getTotalPages()))
                .body(page.getContent());
    }

    @GetMapping("/api/address/{id}")
    public ResponseEntity<Address> addressById(@PathVariable("id") Integer id,Authentication authentication) throws AddressNotExistsException {
        if(! validateRol(authentication)){
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }
        else{
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/api/address2", produces = "application/json")
    public ResponseEntity<List<Address>> allAddress2(Pageable pageable, Authentication authentication) {
        if (validateRol(authentication)){
        Page page = addressService.allAddress(pageable);
        return responseAddress(page);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE') or hasAuthority('CLIENT') ")
    @GetMapping( "/api/address/user/{id}")
    public ResponseEntity<Address> addressByIdUser(@PathVariable("id") Integer id, Authentication authentication) throws AddressNotExistsException {
        Address address2= new Address();
        if (validateRol(authentication)){
            address2 = addressService.getAddressById(id);
        }
        return ResponseEntity.ok(address2);
    }

    @DeleteMapping("/api/address/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable(name = "id") Integer id,Authentication authentication) {
        if(! validateRol(authentication)){
        return addressService.deleteById(id);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/api/address/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable("id") Integer id,@RequestBody AddressDto addressDto, Authentication authentication) throws AddressNotExistsException {
        if(! validateRol(authentication)){
            addressService.updateAddress(id,addressDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("")
                    .buildAndExpand(id)
                    .toUri();
            return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/address/{id}/user/{userId}", produces = "application/json")
    public ResponseEntity<Usuario> addAddressToUser(@PathVariable("id") Integer id,@PathVariable("userId") Integer userId,Authentication authentication){
        if ( !(validateRol(authentication))){
            addressService.addUserToAddress(id,userId);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return null;
    }

    public boolean validateRol(Authentication authentication){
        boolean isUser= false;
        Usuario user = usuarioService.getUserById(((UserDto)authentication.getPrincipal()).getId());
        if(user.getTypeUser().getName().equals(CLIENT)){
            isUser= true;
        }
        return isUser;
    }
    /*-------------------------------------------------------------------------------------------------------------*/

    @PostMapping(value = "/api/meter",consumes = "application/json")
    public ResponseEntity newMeter(@RequestBody Meter meter,Authentication authentication){
        if( ! validateRol(authentication)){
            Meter newMeasure = meterService.newMeter(meter);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newMeasure.getId())
                    .toUri();
            return new ResponseEntity<>(location,(HttpStatus.CREATED));
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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

    @GetMapping(value = "/api/meter", produces = "application/json")
    public ResponseEntity<List<Meter>> allMeters(Pageable pageable,Authentication authentication) {
        if( ! validateRol(authentication)){
            Page page = meterService.allMeter(pageable);
            return response(page);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private ResponseEntity response(Page page) {

        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements()))
                .header("X-Total-Pages", Long.toString(page.getTotalPages()))
                .body(page.getContent());
    }

    @GetMapping(value = "/api/meters/{id}", produces = "application/json")
    public ResponseEntity<Meter> meterById(@PathVariable("id") Integer id,Authentication authentication) throws MeterNotExistsException {
        if( ! validateRol(authentication)){
            Meter measure = meterService.getMeterById(id);
            return ResponseEntity.ok(measure);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/api/meter/{id}")
    public ResponseEntity<?> deleteMeter(@PathVariable(name = "id") Integer id,Authentication authentication) {
        if( ! validateRol(authentication)){
            return meterService.deleteById(id);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/api/meter/{id}")
    public ResponseEntity<?> updateMeter(@PathVariable("id") Integer id,@RequestBody MeterDto meterDto,Authentication authentication) throws MeterNotExistsException {
        if( ! validateRol(authentication)){
            meterService.updateMeter(id,meterDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("")
                    .buildAndExpand(id)
                    .toUri();
            return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
