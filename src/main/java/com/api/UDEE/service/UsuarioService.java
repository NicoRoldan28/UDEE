package com.api.UDEE.service;

import com.api.UDEE.domain.Usuario;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository=usuarioRepository;
    }

    public Usuario login(String username, String password) {
        return Optional.ofNullable(usuarioRepository.findByUsernameAndPassword(username, password)).orElseThrow(() -> new RuntimeException("User does not exists"));
    }

    public Usuario getUserById(Integer id) throws AddressNotExistsException {
        return usuarioRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public Usuario newUser(Usuario user) {
            return usuarioRepository.save(user);
    }

    public Page allUsers(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

}
