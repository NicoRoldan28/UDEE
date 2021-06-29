package com.api.UDEE.controller;

import com.api.UDEE.domain.Address;
import com.api.UDEE.domain.Bill;
import com.api.UDEE.domain.Usuario;
import com.api.UDEE.dto.BillDto;
import com.api.UDEE.dto.MeasurementsDto;
import com.api.UDEE.dto.UserDto;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.exceptions.notFound.BillNotExistsException;
import com.api.UDEE.service.AddressService;
import com.api.UDEE.service.BillService;
import com.api.UDEE.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.core.Authentication;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class BillController {

    private BillService billService;
    private UsuarioService usuarioService;

    @Autowired
    private AddressService addressService;

    private static final String CLIENT = "CLIENT";

    @Autowired
    public BillController(BillService billService,UsuarioService usuarioService){

        this.billService=billService;
        this.usuarioService= usuarioService;
    }

    @PostMapping(value= "/api/bills",consumes = "application/json")
    public ResponseEntity newBill(@RequestBody Bill bill)  {

        Bill newBill = billService.newBill(bill);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newBill.getId())
                .toUri();
        return new ResponseEntity<>(location,(HttpStatus.CREATED));
    }

    @GetMapping(value = "/api/bills")
    public ResponseEntity<List<Bill>> allBills(Pageable pageable) {
        Page page = billService.allBills(pageable);
        return response(page);
    }

    @GetMapping(value = "/api/bills/{id}", produces = "application/json")
    public ResponseEntity<Bill> billByCode(@PathVariable("id") Integer id) throws BillNotExistsException {
        Bill bill = billService.getBillById(id);
        return ResponseEntity.ok(bill);
    }

    /*2) Consulta de facturas por rango de fechas.*/
    @PreAuthorize(value = "hasAuthority('EMPLOYEE') or hasAuthority('CLIENT') ")
    @GetMapping(value = "/api/bills/dates", produces = "application/json")
    public ResponseEntity<List<BillDto>> billByDates(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date since,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date until, Authentication authentication, Pageable pageable ) throws AddressNotExistsException {
        List<BillDto> listBills;
        Page<BillDto> pages;
        if (validateRol(authentication)){
            listBills= billService.allBillsByDates(since,until,((UserDto)authentication.getPrincipal()).getId());
            pages = new PageImpl<>(listBills, pageable, listBills.size());
            return response(pages);
        }
        else {
            return (ResponseEntity<List<BillDto>>) ResponseEntity.status(HttpStatus.FORBIDDEN);
        }
    }

    /*3) Consulta de deuda (Facturas impagas)*/
    @GetMapping(value = "/api/bills/unpaid", produces = "application/json")
    public ResponseEntity<List<BillDto>> billUnpaid(@RequestParam @PathVariable Integer idAddress,Authentication authentication, Pageable pageable) throws NullPointerException {

        Usuario user = usuarioService.getUserById(((UserDto)authentication.getPrincipal()).getId());
        Address address = addressService.getAddressById(idAddress);
        List<BillDto> list;
            if (validateRol(authentication)){
                list= (List<BillDto>) billService.allBillUnpaidByUserAndAddress(((UserDto)authentication.getPrincipal()).getId(),idAddress);
                    Page<BillDto> pages = new PageImpl<BillDto>(list, pageable, list.size());
                    return response(pages);
            }
            else {
                return (ResponseEntity<List<BillDto>>) ResponseEntity.status(HttpStatus.FORBIDDEN);
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

    public boolean validateRol(Authentication authentication) throws AddressNotExistsException {
        boolean isUser= false;
        Usuario user = usuarioService.getUserById(((UserDto)authentication.getPrincipal()).getId());
        if(user.getTypeUser().getName().equals(CLIENT)){
            isUser= true;
        }
        return isUser;
    }
}
