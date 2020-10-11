package com.scalablecapital.currencyapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;
import com.scalablecapital.currencyapi.service.CurrencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {


    @Mock
    CurrencyService currencyService;

    @InjectMocks
    CurrencyController currencyController;

    MockMvc mockMvc;

    @Test
    public void currenciesReferenceRate() throws Exception {
        mockMvc = standaloneSetup(currencyController).build();

        ReferenceRateDto referenceRateDto = new ReferenceRateDto();
        referenceRateDto.setCurrencyPair("ckk");

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(referenceRateDto);

        Mockito.when(currencyService.getCurrenciesReferenceRate(any(), any())).thenReturn(referenceRateDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/currencies/reference-rate?source=DKK&target=EUR")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStr))
                .andExpect(header().string("Content-Type","application/json"));
    }

    @Test
    public void convertCurrencies() {
    }

    @Test
    public void getAllCurrencies() {
    }
}