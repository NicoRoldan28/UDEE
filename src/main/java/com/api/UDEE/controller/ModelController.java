package com.api.UDEE.controller;

import com.api.UDEE.domain.Address;
import com.api.UDEE.domain.Model;
import com.api.UDEE.domain.User;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.ModelService;
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
@RequestMapping(value = "/api/models")
public class ModelController {

    private ModelService modelService;
    //private ModelMapper modelMapper;

    @Autowired
    public ModelController(ModelService modelService){
        this.modelService=modelService;
        //this.modelMapper=modelMapper;
    }

    //@PreAuthorize(value = "hasAuthority(TypeUser.CLIENT)")
    @PreAuthorize(value = "hasAuthority('CLIENT')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity newModel(@RequestBody Model model){
        Model newModel = modelService.newModel(model);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newModel.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/models")
    public ResponseEntity<List<Model>> allModels(Pageable pageable) {
        Page page = modelService.allModels(pageable);
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
    public ResponseEntity<Model> ModelByCode(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Model model = modelService.getModelById(id);
        return ResponseEntity.ok(model);
    }
}
