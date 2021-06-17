package com.api.UDEE.controller;

import com.api.UDEE.domain.TypeUser;
import com.api.UDEE.domain.User;
import com.api.UDEE.domain.Usuario;
import com.api.UDEE.dto.LoginRequestDto;
import com.api.UDEE.dto.LoginResponseDto;
import com.api.UDEE.dto.UserDto;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.api.UDEE.Utils.Constants.JWT_SECRET;

@Slf4j
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public UsuarioController(UsuarioService usuarioService,ModelMapper modelMapper, ObjectMapper objectMapper){
        this.usuarioService=usuarioService;
        this.modelMapper=modelMapper;
        this.objectMapper=objectMapper;
    }

    private String generateToken(UserDto userDto, TypeUser typeUser) {
        try {
            String authRole;
            if(typeUser.equals("CLIENT")) {
                authRole="CLIENT";
            }
            else {
                authRole="EMPLOYEE";
            }
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authRole);
            String token = Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(userDto.getUsername())
                    .claim("user", objectMapper.writeValueAsString(userDto))
                    .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
            return  token;
        } catch(Exception e) {
            return "dummy";
        }
    }

    @PostMapping(value = "auth/login2")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Usuario user = usuarioService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        if (user!=null){
            UserDto dto = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok(LoginResponseDto.builder().token(this.generateToken(dto,user.getTypeUser())).build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "users2",consumes = "application/json")
    public ResponseEntity newClient(@RequestBody Usuario user){
        Usuario newUser = usuarioService.newUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users2")
    public ResponseEntity<List<Usuario>> allUsers(Pageable pageable) {
        Page page = usuarioService.allUsers(pageable);
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

    @GetMapping(value = "/users2/{id}", produces = "application/json")
    public ResponseEntity<Usuario> userByCode(@PathVariable("id") Integer id) throws AddressNotExistsException {
        Usuario user = usuarioService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
