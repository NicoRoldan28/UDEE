package com.api.UDEE.service;

import com.api.UDEE.domain.User;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User getUserById(Integer id) throws AddressNotExistsException {
        return userRepository.findById(id).orElseThrow(()-> new AddressNotExistsException("No se encontro una bill con ese id"));
    }

    public User newUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        }
        else{
            return null;
        }
    }

    public Page allUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
