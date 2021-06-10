package com.api.UDEE.service;

import com.api.UDEE.domain.Usuario;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.UsuarioRepository;
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

import static com.api.UDEE.utils.TestUtils.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    /*@Mock
    private UsuarioRepository usuarioRepository;
*/

    @Test
    void getUserById() throws AddressNotExistsException {
        Usuario usuario= aUsuario();
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        Usuario response = usuarioService.getUserById(1);
        assertNotNull(response);
        assertEquals(usuario, response);
    }

    @Test
    void shouldSaveUser() throws AddressNotExistsException {

        Usuario usuario= aUsuario();

        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario);

        assertNotNull(usuario);

        usuarioRepository.save(usuario);

    }

    @Test
    void badRequestSaveUser(){
        Usuario usuario= aUsuario();

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        //when(usuarioService.getReduceUser(user.getIdUser())).thenReturn(userReduce);
        when(usuarioRepository.findById(usuario.getId())).thenReturn(null);

        //assertEquals(400, response.getStatusCodeValue());
        //assertNull(response.getBody());
    }

    @Test
    void shouldLogin(){
        Usuario usuario= new Usuario();//(1,"nico","1234");
        String username="nico";
        String password="1234";
        usuario.setUsername(username);
        usuario.setPassword(password);
        when(usuarioRepository.findByUsernameAndPassword(username,password)).thenReturn(usuario);
        Usuario response= usuarioService.login(username,password);

        assertNotNull(response);
        assertEquals(usuario,response);
    }


    @Test
    void badRequestLogin(){
        Usuario usuario= new Usuario();
        String username="nico";
        String password="1234";
        usuario.setUsername(username);
        usuario.setPassword(password);

        Mockito.when(usuarioRepository.findByUsernameAndPassword(username, password)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            usuarioService.login(username, password);
        });
    }

    @Test
    void shouldGetAll() {
        /*Usuario usuario= new Usuario(1,"nicolas","roldan");
        Usuario usuario2= new Usuario(2,"rodrigo","villarroel");
        List<Usuario> usersList = new ArrayList<>();
        usersList.add(usuario);
        usersList.add(usuario2);
        Page<Usuario> user = new PageImpl<>(usersList);

        when(usuarioRepository.findAll()).thenReturn(user);
        Page response = usuarioService.allUsers((Pageable) user);

        assertNotNull(response);
        assertEquals(usersList, response);
*/

        Pageable pageable = aPageable();
        Page<Usuario> page = aUsuarioPage();

        when(usuarioRepository.findAll(pageable)).thenReturn(page);

        Page<Usuario> u = usuarioService.allUsers(pageable);

        //assertEquals(page.getContent().get(0).getDni(),c.getContent().get(0).getDni());
        assertEquals(page.getContent().get(0).getId(),u.getContent().get(0).getId());
        //assertEquals(pageable.getPageNumber(),u.getTotalPages());
    }
}
