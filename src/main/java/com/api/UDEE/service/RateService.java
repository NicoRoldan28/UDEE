package com.api.UDEE.service;

import com.api.UDEE.Utils.EntityURLBuilder;
import com.api.UDEE.domain.PostResponse;
import com.api.UDEE.domain.Rate;
import com.api.UDEE.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateService {
    private final RateRepository rateRepository;
    private static final String RATE_PATH = "rate";
    @Autowired
    public RateService(RateRepository rateRepository){
        this.rateRepository=rateRepository;
    }

    public Optional<Rate> getRateById(Integer id) {
        return rateRepository.findById(id);
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

}
