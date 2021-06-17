package com.api.UDEE.service;

import com.api.UDEE.domain.Brand;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    @Autowired
    public BrandService(BrandRepository brandRepository){
        this.brandRepository=brandRepository;
    }

    public Brand getBrandById(Integer id) throws AddressNotExistsException {
        return brandRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public Brand newBrand(Brand brand) {
            return brandRepository.save(brand);
    }

    public Page allBrands(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

}
