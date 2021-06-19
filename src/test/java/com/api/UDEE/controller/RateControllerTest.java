package com.api.UDEE.controller;

import com.api.UDEE.AbstractController;
import com.api.UDEE.service.RateService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.api.UDEE.utils.TestUtils.aRateJSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(classes = RateController.class)
public class RateControllerTest extends AbstractController{
    @MockBean
    private RateService rateService;

    @Test
    public void allRate() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/api/rates")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    public void getRateById() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/rates/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    public void newRate() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/api/rates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aRateJSON()))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    /*
    @Test
    public void addPersonBadRequest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/api/rates")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertEquals(HttpStatus.BAD_REQUEST.value(), resultActions.andReturn().getResponse().getStatus());
    }*/

}
