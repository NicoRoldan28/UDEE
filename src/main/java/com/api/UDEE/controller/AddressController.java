package com.api.UDEE.controller;

import com.api.UDEE.domain.Address;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.AddressService;
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
@RequestMapping(value = "/api/address")
public class AddressController {

    private AddressService addressService;
    private ModelMapper modelMapper;

    @Autowired
    public AddressController(AddressService addressService, ModelMapper modelMapper){
        this.addressService=addressService;
        this.modelMapper=modelMapper;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity newAddress(@RequestBody Address address){
        Address newAddress = addressService.newAddress(address);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAddress.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping()
    public ResponseEntity<List<Address>> allAddress(Pageable pageable) {
        Page page = addressService.allAddress(pageable);
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
    public ResponseEntity<Address> addressById(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

}
