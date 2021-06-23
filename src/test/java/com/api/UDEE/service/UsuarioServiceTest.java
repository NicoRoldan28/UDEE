package com.api.UDEE.service;

import com.api.UDEE.repository.UsuarioRepository;
import org.junit.Before;

//import static com.api.UDEE.utils.TestUtils.*;
import static org.mockito.Mockito.*;

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
