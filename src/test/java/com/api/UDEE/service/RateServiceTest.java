package com.api.UDEE.service;

import com.api.UDEE.domain.Rate;
import com.api.UDEE.repository.RateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import static com.api.UDEE.utils.TestUtils.*;

import static org.mockito.Mockito.mock;


public class RateServiceTest {

    RateService rateService;
    RateRepository rateRepository;

    @Before
    public void setUp(){
        rateRepository= mock(RateRepository.class);
        rateService= new RateService(rateRepository);
    }

    @Test
    public void shouldSaveUser(){

        Rate rate= aRate();

        Mockito.when(rateRepository.save(rate)).thenReturn(rate);

        Rate actRate=  rateService.newRate(rate);

        Assert.assertEquals(rate,actRate);
        //rateRepository.save(rate);
    }
}
