package com.scalablecapital.currencyapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalablecapital.currencyapi.dto.CurrencyConversionDto;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.exception.CurrencyNotFoundException;
import com.scalablecapital.currencyapi.service.CurrencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {

    @Mock
    CurrencyService currencyService;

    @InjectMocks
    CurrencyController currencyController;

    @Test
    public void currenciesReferenceRate_404() throws Exception {
        MockMvc mockMvc = standaloneSetup(currencyController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/somethingWrong/currencies/reference-rate?source=USD&target=EUR")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void currenciesReferenceRate() throws Exception {
        MockMvc mockMvc = standaloneSetup(currencyController).build();

        ReferenceRateDto referenceRateDto = new ReferenceRateDto();
        referenceRateDto.setCurrencyPair("USD/EUR");
        referenceRateDto.setLink("someLink");
        referenceRateDto.setReferenceRate(12.12);

        ObjectMapper mapper = new ObjectMapper();
        String referenceRateJson = mapper.writeValueAsString(referenceRateDto);

        Mockito.when(currencyService.getCurrenciesReferenceRate(any(), any())).thenReturn(referenceRateDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/exchange-rate-service/currencies/reference-rate?source=USD&target=EUR")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(referenceRateJson))
                .andExpect(header().string("Content-Type", "application/json"));
    }

    @Test
    public void convertCurrencies() throws Exception {

        MockMvc mockMvc = standaloneSetup(currencyController).build();

        CurrencyConversionDto currencyConversionDto = new CurrencyConversionDto();
        currencyConversionDto.setSource("EUR");
        currencyConversionDto.setSource("GBP");
        currencyConversionDto.setSourceAmount(18.0);
        currencyConversionDto.setTargetAmount(66.0);

        ObjectMapper mapper = new ObjectMapper();
        String referenceRateJson = mapper.writeValueAsString(currencyConversionDto);

        Mockito.when(currencyService.getCurrencyConversion(any(), anyDouble(), any())).thenReturn(currencyConversionDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/exchange-rate-service/currencies/convert-rate?source=EUR&target=GBP&sourceAmount=18")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(referenceRateJson))
                .andExpect(header().string("Content-Type", "application/json"));
    }

    @Test
    public void getAllCurrencies() throws Exception {

        MockMvc mockMvc = standaloneSetup(currencyController).build();

        final Currency USD = new Currency(1, "US dollar", "USD", 1.1795, 0L);
        final Currency EUR = new Currency(8, "Europe", "EUR", 1, 0L);

        List<Currency> currencyList = Arrays.asList(USD, EUR);

        ObjectMapper mapper = new ObjectMapper();
        String currencyListJson = mapper.writeValueAsString(currencyList);

        Mockito.when(currencyService.getAllCurrencies()).thenReturn(currencyList);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/exchange-rate-service/currencies")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(currencyListJson))
                .andExpect(header().string("Content-Type", "application/json"));
    }
}