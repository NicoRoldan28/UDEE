package com.api.UDEE.service;

import com.api.UDEE.Utils.EntityURLBuilder;
import com.api.UDEE.domain.Meter;
import com.api.UDEE.domain.PostResponse;
import com.api.UDEE.domain.Rate;
import com.api.UDEE.dto.RatesDto;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RateService {
    private final RateRepository rateRepository;
    private static final String RATE_PATH = "rate";
    @Autowired
    public RateService(RateRepository rateRepository){
        this.rateRepository=rateRepository;
    }

    public Rate getRateById(Integer id) throws AddressNotExistsException {
        return rateRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public PostResponse newRate(Rate rate) {
        Rate r = rateRepository.save(rate);
        return PostResponse
                .builder()
                .status(HttpStatus.CREATED)
                .url(EntityURLBuilder.buildURL(RATE_PATH, r.getId()))
                .build();
    }

    public Page allRates(Pageable pageable){
        return this.rateRepository.findAll(pageable);
    }

    public ResponseEntity<?> deleteById(Integer id) {
        try {
            rateRepository.deleteById(id);
            return new ResponseEntity<>("Se ha eliminado la tarifa con éxito.", HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("La tarifa con el id " + id + " es inexistente.", HttpStatus.NOT_FOUND);
        }
    }

    public void updateRates(Integer id, RatesDto ratesDto) throws AddressNotExistsException {
        Rate rate=this.getRateById(id);
        rate.setPrice(ratesDto.getPrice());
        rateRepository.save((rate));
    }
}
