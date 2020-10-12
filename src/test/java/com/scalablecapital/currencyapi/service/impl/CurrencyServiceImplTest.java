package com.scalablecapital.currencyapi.service.impl;

import com.scalablecapital.currencyapi.repository.CurrencyRepository;
import com.scalablecapital.currencyapi.dto.CurrencyConversionDto;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.exception.CurrencyNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

    @Mock
    CurrencyRepository currencyRepository;

    @InjectMocks
    CurrencyServiceImpl currencyService;

    @Before
    public void setUpDB(){

        /*
        DataHolder.currencyDB.clear();

        Currency USD = new Currency(1,"US dollar","USD",1.1795,0L);
        Currency JPY = new Currency(2,"Japanese yen","JPY",124.95,0L);
        Currency BGN = new Currency(3,"Bulgarian lev","BGN",1.9558,0L);
        Currency CZK = new Currency(4,"Czech koruna","CZK",27.110,0L);
        Currency DKK = new Currency(5,"Danish krone","DKK",7.4422,0L);
        Currency GBP = new Currency(6,"Pound sterling","GBP",0.91167,0L);
        Currency HUF = new Currency(7,"Hungarian forint","HUF",356.28,0L);
        Currency EUR = new Currency(8,"Europe","EUR",1,0L);

        DataHolder.currencyDB.add(USD);
        DataHolder.currencyDB.add(JPY);
        DataHolder.currencyDB.add(BGN);
        DataHolder.currencyDB.add(CZK);
        DataHolder.currencyDB.add(DKK);
        DataHolder.currencyDB.add(GBP);
        DataHolder.currencyDB.add(HUF);
        DataHolder.currencyDB.add(EUR);
        */
    }

    @Test
    public void referenceRateForUSD_EUR() {

        Currency USD = new Currency(1,"US dollar","USD",1.1795,0L);
        Currency EUR = new Currency(8,"Europe","EUR",1,0L);
        Mockito.when(currencyRepository.findCurrency("USD")).thenReturn(USD);
        Mockito.when(currencyRepository.findCurrency("EUR")).thenReturn(EUR);

        String link = "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-usd.en.html";

        ReferenceRateDto referenceRateDto = currencyService.getCurrenciesReferenceRate("USD","EUR");

        assertNotNull(referenceRateDto);
        assertEquals("USD/EUR",referenceRateDto.getCurrencyPair());
        assertEquals(Double.valueOf(1.1795),referenceRateDto.getReferenceRate());
        assertEquals(link,referenceRateDto.getLink());
    }

    @Test
    public void referenceRateForJPY_USD() {

        Currency USD = new Currency(1,"US dollar","USD",1.1795,0L);
        Currency JPY = new Currency(2,"Japanese yen","JPY",124.95,0L);
        Mockito.when(currencyRepository.findCurrency("USD")).thenReturn(USD);
        Mockito.when(currencyRepository.findCurrency("JPY")).thenReturn(JPY);

        String link = "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-jpy.en.html";

        ReferenceRateDto referenceRateDto = currencyService.getCurrenciesReferenceRate("JPY","USD");

        assertNotNull(referenceRateDto);
        assertEquals("JPY/USD",referenceRateDto.getCurrencyPair());
        assertEquals(Double.valueOf(105.9347),referenceRateDto.getReferenceRate());
        assertEquals(link,referenceRateDto.getLink());
    }

    @Test(expected = CurrencyNotFoundException.class)
    public void referenceRateWithMalformData() {
        currencyService.getCurrenciesReferenceRate("ABCDEF","EUR");
    }

    @Test
    public void getAllCurrencies() {

        currencyService.getCurrenciesReferenceRate("JPY","USD");
        currencyService.getCurrenciesReferenceRate("JPY","USD");
        currencyService.getCurrenciesReferenceRate("JPY","EUR");
        currencyService.getCurrencyConversion("JPY",15,"EUR");

        List<Currency> currencies = currencyService.getAllCurrencies();

        Currency jpy = currencies.stream()
                .filter(currency -> currency.getAbbreviation().equals("JPY")).findFirst().orElse(null);

        Currency eur = currencies.stream()
                .filter(currency -> currency.getAbbreviation().equals("EUR")).findFirst().orElse(null);

        assertNotNull(jpy);
        assertEquals(4,jpy.getVisits());

        assertNotNull(eur);
        assertEquals(2,eur.getVisits());
    }

    @Test
    public void currencyConversionForUSD_EUR() {
        CurrencyConversionDto conversionDto = currencyService.getCurrencyConversion("USD",15,"EUR");

        assertNotNull(conversionDto);
        assertEquals("USD",conversionDto.getSource());
        assertEquals(Double.valueOf(15),conversionDto.getSourceAmount());
        assertEquals("EUR",conversionDto.getTarget());
        assertEquals(Double.valueOf(12.7173),conversionDto.getTargetAmount());
    }
}