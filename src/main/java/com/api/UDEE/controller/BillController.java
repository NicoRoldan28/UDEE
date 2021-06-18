package com.api.UDEE.controller;

import com.api.UDEE.domain.Bill;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.core.Authentication;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
//@RequestMapping(value = "/api/bills")
public class BillController {

    private BillService billService;
    private ModelMapper modelMapper;

    @Autowired
    public BillController(BillService billService, ModelMapper modelMapper){
        this.billService=billService;
        this.modelMapper=modelMapper;
    }

    @PostMapping(value= "/api/bills",consumes = "application/json")
    public ResponseEntity newCountry(@RequestBody Bill bill)  {
        Bill newBill = billService.newBill(bill);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newBill.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/api/bills")
    public ResponseEntity<List<Bill>> allBills(Pageable pageable) {
        Page page = billService.allBills(pageable);
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

    @GetMapping(value = "/api/bills/{id}", produces = "application/json")
    public ResponseEntity<Bill> billByCode(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Bill bill = billService.getBillById(id);
        return ResponseEntity.ok(bill);
    }

    @GetMapping(value = "/api/bills/dates", produces = "application/json")
    public ResponseEntity<List<Bill>> billByDates(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to ){
        List<Bill> listBills = new ArrayList<Bill>();
              listBills= billService.allBillsByDates(from,to);
        return (ResponseEntity<List<Bill>>) listBills;
    }

    @GetMapping(value = "/api/bills/unpaid", produces = "application/json")
    public ResponseEntity<List<Bill>> billUnpaid(Authentication authentication){
        List<Bill> listBill = new ArrayList<Bill>();
        listBill= billService.allBillUnpaid();
        return (ResponseEntity<List<Bill>>) listBill;
    }

}
