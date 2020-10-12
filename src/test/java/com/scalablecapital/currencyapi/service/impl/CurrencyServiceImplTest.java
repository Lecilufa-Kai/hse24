package com.scalablecapital.currencyapi.service.impl;

import com.scalablecapital.currencyapi.dto.CurrencyConversionDto;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.exception.CurrencyNotFoundException;
import com.scalablecapital.currencyapi.repository.CurrencyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

    @Mock
    CurrencyRepository currencyRepository;

    @InjectMocks
    CurrencyServiceImpl currencyService;

    @Test
    public void referenceRateForUSD_EUR() {

        final String sourceCurrency = "USD";
        final String targetCurrency = "EUR";

        final Currency USD = new Currency(1,"US dollar","USD",1.1795,0L);
        final Currency EUR = new Currency(8,"Europe","EUR",1,0L);
        Mockito.when(currencyRepository.findCurrency("USD")).thenReturn(USD);
        Mockito.when(currencyRepository.findCurrency("EUR")).thenReturn(EUR);

        String link = "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-usd.en.html";

        ReferenceRateDto referenceRateDto = currencyService.getCurrenciesReferenceRate(sourceCurrency,targetCurrency);

        assertNotNull(referenceRateDto);
        assertEquals(sourceCurrency +"/" +targetCurrency,referenceRateDto.getCurrencyPair());
        assertEquals(Double.valueOf(1.1795),referenceRateDto.getReferenceRate());
        assertEquals(link,referenceRateDto.getLink());

        Mockito.verify(currencyRepository, Mockito.times(1)).updateCurrencyVisits(USD);
        Mockito.verify(currencyRepository, Mockito.times(1)).updateCurrencyVisits(EUR);
    }

    @Test
    public void referenceRateForJPY_USD() {

        final String sourceCurrency = "JPY";
        final String targetCurrency = "USD";

        final Currency USD = new Currency(1,"US dollar","USD",1.1795,0L);
        final Currency JPY = new Currency(2,"Japanese yen","JPY",124.95,0L);
        Mockito.when(currencyRepository.findCurrency("USD")).thenReturn(USD);
        Mockito.when(currencyRepository.findCurrency("JPY")).thenReturn(JPY);

        String link = "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-jpy.en.html";

        ReferenceRateDto referenceRateDto = currencyService.getCurrenciesReferenceRate(sourceCurrency,targetCurrency);

        assertNotNull(referenceRateDto);
        assertEquals(sourceCurrency +"/" +targetCurrency,referenceRateDto.getCurrencyPair());
        assertEquals(Double.valueOf(105.9347),referenceRateDto.getReferenceRate());
        assertEquals(link,referenceRateDto.getLink());

        Mockito.verify(currencyRepository, Mockito.times(1)).updateCurrencyVisits(USD);
        Mockito.verify(currencyRepository, Mockito.times(1)).updateCurrencyVisits(JPY);
    }

    @Test(expected = CurrencyNotFoundException.class)
    public void referenceRateWithMalformData() {

        final String malformCurrency = "ABCDEF";

        Mockito.when(currencyRepository.findCurrency(malformCurrency)).thenThrow(new CurrencyNotFoundException("currency not found"));
        currencyService.getCurrenciesReferenceRate(malformCurrency,"EUR");
    }

    @Test
    public void currencyConversionForUSD_EUR() {

        final String sourceCurrency = "USD";
        final String targetCurrency = "EUR";
        final double sourceAmount = 15;

        final Currency USD = new Currency(1,"US dollar","USD",1.1795,0L);
        final Currency EUR = new Currency(8,"Europe","EUR",1,0L);
        Mockito.when(currencyRepository.findCurrency("USD")).thenReturn(USD);
        Mockito.when(currencyRepository.findCurrency("EUR")).thenReturn(EUR);

        CurrencyConversionDto conversionDto = currencyService.getCurrencyConversion(sourceCurrency,sourceAmount,targetCurrency);

        assertNotNull(conversionDto);
        assertEquals(sourceCurrency,conversionDto.getSource());
        assertEquals(Double.valueOf(sourceAmount),conversionDto.getSourceAmount());
        assertEquals(targetCurrency,conversionDto.getTarget());
        assertEquals(Double.valueOf(12.7173),conversionDto.getTargetAmount());

        Mockito.verify(currencyRepository, Mockito.times(1)).updateCurrencyVisits(USD);
        Mockito.verify(currencyRepository, Mockito.times(1)).updateCurrencyVisits(EUR);
    }

    @Test
    public void currencyConversionForUSD_HUF() {

        final String sourceCurrency = "USD";
        final String targetCurrency = "HUF";
        final double sourceAmount = 18;

        Currency USD = new Currency(1,"US dollar","USD",1.1795,0L);
        Currency HUF = new Currency(7,"Hungarian forint","HUF",356.28,0L);
        Mockito.when(currencyRepository.findCurrency("USD")).thenReturn(USD);
        Mockito.when(currencyRepository.findCurrency("HUF")).thenReturn(HUF);

        CurrencyConversionDto conversionDto = currencyService.getCurrencyConversion(sourceCurrency,sourceAmount,targetCurrency);

        assertNotNull(conversionDto);
        assertEquals(sourceCurrency,conversionDto.getSource());
        assertEquals(Double.valueOf(sourceAmount),conversionDto.getSourceAmount());
        assertEquals(targetCurrency,conversionDto.getTarget());
        assertEquals(Double.valueOf(5437.0835),conversionDto.getTargetAmount());

        Mockito.verify(currencyRepository, Mockito.times(1)).updateCurrencyVisits(USD);
        Mockito.verify(currencyRepository, Mockito.times(1)).updateCurrencyVisits(HUF);
    }
}