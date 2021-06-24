package com.api.UDEE.service;

import com.api.UDEE.domain.Address;
import com.api.UDEE.domain.Usuario;
import com.api.UDEE.dto.AddressDto;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.exceptions.notFound.UsuarioNotExistsException;
import com.api.UDEE.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UsuarioService usuarioService;
    @Autowired
    public AddressService(AddressRepository addressRepository,UsuarioService usuarioService){
        this.addressRepository=addressRepository;
        this.usuarioService=usuarioService;
    }

    public Address getAddressById(Integer id) throws AddressNotExistsException {
        return addressRepository.findById(id).orElseThrow(()-> new AddressNotExistsException("No Address was found by that id"));
    }

    public Address newAddress(Address address) {
            return addressRepository.save(address);
    }

    public Page allAddress(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    /*public List<Address> getAddressByIdUser(Integer id){
        return addressRepository.findByUserId(id);
    }*/

    public ResponseEntity<?> deleteById(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ResponseEntity<>("Se ha eliminado la direccion con éxito.", HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("La direccion con el id " + id + " es inexistente.", HttpStatus.NOT_FOUND);
        }
    }

    public void updateAddress(Integer id, AddressDto addressDto) throws AddressNotExistsException {
        Address address=this.getAddressById(id);
        List<AddressDto> list=new ArrayList<AddressDto>();
        list.add(addressDto);
        for ( AddressDto a: list){
            if (a.getNumber()!=null) {
                address.setNumber(addressDto.getNumber());
            }
            if (a.getStreet()!=null){
                address.setStreet(addressDto.getStreet());
            }
            if (a.getRate()!=null){
                address.setRate(addressDto.getRate());
            }
        }
        addressRepository.save((address));
    }

    public Object addUserToAddress(Integer id, Integer userId) throws UsuarioNotExistsException {
        Usuario user= this.usuarioService.getUserById(userId);
        Address address= getAddressById(id);

        if((user != null)&&(address!=null)){

            address.setUserClient(user);

            addressRepository.save(address);
            return user;
        }
        else if (user == null){
            return new UsuarioNotExistsException("No user was found by that id");
        }
        else {
            return new AddressNotExistsException("No address was found by that id");
        }
    }
}
