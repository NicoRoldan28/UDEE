package com.api.UDEE.service;

import com.api.UDEE.domain.Model;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.exceptions.notFound.ModelNotExistsException;
import com.api.UDEE.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ModelService {
    private final ModelRepository modelRepository;
    @Autowired
    public ModelService(ModelRepository modelRepository){
        this.modelRepository=modelRepository;
    }

    public Model getModelById(Integer id) throws ModelNotExistsException {
        return modelRepository.findById(id).orElseThrow(()-> new ModelNotExistsException("No Model was found by that id"));
    }

    public Model newModel(Model model) {
            return modelRepository.save(model);
    }

    public Page allModels(Pageable pageable) {
        return modelRepository.findAll(pageable);
    }

}
