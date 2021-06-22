package com.api.UDEE.service;

import com.api.UDEE.domain.Usuario;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static com.api.UDEE.utils.TestUtils.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioService usuarioService;

    private UsuarioRepository usuarioRepository;

    @Before
    public void setUp(){

        usuarioRepository=mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }
    /*
    @Test
    void shouldLogin(){
        Usuario usuario= new Usuario();//(1,"nico","1234");
        String username="nico";
        String password="1234";
        usuario.setUsername(username);
        usuario.setPassword(password);
        when(this.usuarioRepository.findByUsernameAndPassword(username,password)).thenReturn(usuario);
        //when(usuarioRepository.findByUsernameAndPassword(username,password)).thenReturn(usuario);
        Usuario response= usuarioService.login(username,password);

        assertNotNull(response);
        assertEquals(usuario,response);
    }*/

}
