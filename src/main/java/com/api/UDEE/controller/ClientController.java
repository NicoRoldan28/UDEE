package com.api.UDEE.controller;

import com.api.UDEE.domain.Address;
import com.api.UDEE.domain.Client;
import com.api.UDEE.domain.Rate;
import com.api.UDEE.domain.User;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.ClientService;
import com.api.UDEE.service.RateService;
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
@RequestMapping(value = "/api/clients")
public class ClientController {

    private ClientService clientService;
    //private ModelMapper modelMapper;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService=clientService;
        //this.modelMapper=modelMapper;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity newClient(@RequestBody Client client){
        Client newClient = clientService.newClient(client);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newClient.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> allClients(Pageable pageable) {
        Page page = clientService.allClients(pageable);
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
    public ResponseEntity<Client> clientByCode(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }
}
