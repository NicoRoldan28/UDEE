package com.api.UDEE.controller;

import com.api.UDEE.domain.Model;
import com.api.UDEE.domain.Usuario;
import com.api.UDEE.dto.UserDto;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.service.ModelService;
import com.api.UDEE.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/models")
public class ModelController {

    private ModelService modelService;
    private UsuarioService usuarioService;

    private static final String EMPLOYEE = "EMPLOYEE";

    @Autowired
    public ModelController(ModelService modelService, UsuarioService usuarioService){
        this.modelService=modelService;
        this.usuarioService=usuarioService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity newModel(@RequestBody Model model, Authentication authentication){
        if (validateRol(authentication)){
            Model newModel = modelService.newModel(model);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newModel.getId())
                    .toUri();
            return new ResponseEntity<>(location,(HttpStatus.CREATED));
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "",consumes = "application/json")
    public ResponseEntity<List<Model>> allModels(Pageable pageable, Authentication authentication) {
        if(validateRol(authentication)){
            Page page = modelService.allModels(pageable);
            return response(page);
        }
        else {
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

    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<Model> ModelByCode(@PathVariable("id") Integer id,Authentication authentication) throws AddressNotExistsException {
        if (validateRol(authentication)){
            Model model = modelService.getModelById(id);
            return ResponseEntity.ok(model);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
