package com.api.UDEE.controller;

import com.api.UDEE.domain.Usuario;
import com.api.UDEE.dto.UserDto;
import com.api.UDEE.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsuarioControllerTest {

    private UsuarioService usuarioService;
    private  ModelMapper modelMapper;
    private  ObjectMapper objectMapper;

    private UsuarioController usuarioController;

    private static List<Usuario> EMPTY_LIST = Collections.emptyList();
    private static List<Usuario> USUARIO_LIST = List.of(Usuario.builder().username("nico").id(1).build(),
            Usuario.builder().username("nicolas").id(2).build());


    @BeforeEach
    public void setUp() {
        usuarioService = mock(UsuarioService.class);
        usuarioController = new UsuarioController(usuarioService,modelMapper,objectMapper);
    }


     // Vamos a testear que haya paises dentro de lo que se pidio

    @Test
    public void testAllUsersHttpStatus200() {

        //given
        Pageable pageable = PageRequest.of(1, 10);
        Page<Usuario> mockedPage = mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(100L);
        when(mockedPage.getTotalPages()).thenReturn(10);
        when(mockedPage.getContent()).thenReturn(USUARIO_LIST);
        when(usuarioService.allUsers(pageable)).thenReturn(mockedPage);

        //Then
        ResponseEntity<List<Usuario>> response = usuarioController.allUsers(pageable);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100L, Long.parseLong(response.getHeaders().get("X-Total-Count").get(0)) );
        assertEquals(10, Integer.parseInt(response.getHeaders().get("X-Total-Pages").get(0)) );
        assertEquals(USUARIO_LIST, response.getBody());
    }


     //* Vamos a testear que haya paises dentro de lo que se pidio

    @Test
    public void testAllUsersNoContent() {
        //given
        Pageable pageable = PageRequest.of(50, 10);
        Page<Usuario> mockedPage = mock(Page.class);
        when(mockedPage.getContent()).thenReturn(EMPTY_LIST);
        when(usuarioService.allUsers(pageable)).thenReturn(mockedPage);

        //Then
        ResponseEntity<List<Usuario>> response = usuarioController.allUsers(pageable);

        //Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void addClient_Test200(){
            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            //Date birtday = new SimpleDateFormat("yyyy-MM-dd").parse("2020-02-02");
            ServletUriComponentsBuilder location = mock(ServletUriComponentsBuilder.class);

            UserDto registerDto = UserDto.builder()
                    .id(1)
                    .username("nicolas")
                    .build();

            Usuario usuario= Usuario.builder().id(1).username("nicolas").password("1234").build();

            when(usuarioService.newUser(usuario)).thenReturn(usuario);

            ResponseEntity response = usuarioController.newClient(usuario);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
