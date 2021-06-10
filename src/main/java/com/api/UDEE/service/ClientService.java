package com.api.UDEE.service;

import com.api.UDEE.domain.Client;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository=clientRepository;
    }

    public Client getClientById(Integer id) throws AddressNotExistsException {
        return clientRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public Client newClient(Client client) {
        if (!clientRepository.existsById(client.getId())) {
            return clientRepository.save(client);
        }
        else{
            return null;
        }
    }

    public Page allClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

}
