package com.api.UDEE.controller;

import com.api.UDEE.domain.Brand;
import com.api.UDEE.domain.User;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService){
        this.brandService=brandService;
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity newBrand(@RequestBody Brand brand){
        Brand newBrand = brandService.newBrand(brand);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newBrand.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize(value = "hasAuthority('EMPLOYEE')")
    @GetMapping()
    public ResponseEntity<List<Brand>> allBrands(Pageable pageable) {
        Page page = brandService.allBrands(pageable);
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
    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<Brand> brandByCode(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Brand brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }
}
