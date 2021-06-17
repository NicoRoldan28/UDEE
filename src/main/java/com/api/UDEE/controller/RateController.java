package com.api.UDEE.controller;

import com.api.UDEE.domain.PaginationResponse;
import com.api.UDEE.domain.PostResponse;
import com.api.UDEE.domain.Rate;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.RateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/rates")
public class RateController {

    private RateService rateService;
    //private ModelMapper modelMapper;

    @Autowired
    public RateController(RateService rateService){
        this.rateService=rateService;
        //this.modelMapper=modelMapper;
    }


    @PostMapping
    public PostResponse newRate(@RequestBody Rate rate) {
        return rateService.newRate(rate);
    }

    @GetMapping("/rates")
    public ResponseEntity<List<Rate>> allRates(Pageable pageable) {
        Page page = rateService.allRates(pageable);
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
    public ResponseEntity<?> RateByCode(@PathVariable("id") Integer id){
        return new ResponseEntity<>(rateService.getRateById(id), HttpStatus.OK);
    }
}
