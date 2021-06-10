package com.api.UDEE.service;

import com.api.UDEE.domain.Address;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    @Autowired
    public AddressService(AddressRepository addressRepository){
        this.addressRepository=addressRepository;
    }

    public Address getAddressById(Integer id) throws AddressNotExistsException {
        return addressRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public Address newAddress(Address address) {
        if (!addressRepository.existsById(address.getId())) {
            return addressRepository.save(address);
        }
        else{
            return null;
        }
    }

    public Page allAddress(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }
}
