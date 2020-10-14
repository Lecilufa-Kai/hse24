package com.scalablecapital.currencyapi.service;

import com.scalablecapital.currencyapi.dto.CurrencyConversionDto;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CurrencyService {

    /**
     * retrieve the ReferenceRate for the currency pair. eg. source=USD target=EUR it will generate USD/EUR and respectively referenceRate
     * also return a the public link for this pair     *
     * it can receive any currency pair like source=USD target=GBP
     *
     * @param source source currency eg.USD
     * @param target target currency eg.EUR
     * @return  referenceRate and public link {@link ReferenceRateDto}
     */
    @NotNull
    ReferenceRateDto getCurrenciesReferenceRate(@NotNull String source, @NotNull String target);

    /**
     * retrieve the supported currency list and the visits times of each currency     *
     * @return the supported currency list and the visits times of each currency
     */
    @NotNull
    List<Currency> getAllCurrencies();

    /**
     * calculate the conversion from one currency to another. eg. 15 USD = 11.5939 GBP
     *
     * @param source    source currency eg.USD
     * @param sourceAmount source currency amount eg.15
     * @param target    target currency eg.EUR
     * @return  the currency conversion info {@link CurrencyConversionDto}
     */
    @NotNull
    CurrencyConversionDto getCurrencyConversion(@NotNull String source, double sourceAmount, @NotNull String target);
}
