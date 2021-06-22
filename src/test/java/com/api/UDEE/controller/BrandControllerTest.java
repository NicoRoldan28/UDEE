package com.api.UDEE.controller;

import com.api.UDEE.domain.Brand;
import com.api.UDEE.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BrandController.class)
public class BrandControllerTest {

    private BrandService brandService;

    @BeforeEach
    public void setUp(){
        this.brandService= mock(BrandService.class);
    }

    @Test
    public void allBrands() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/api/brands")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }
}
