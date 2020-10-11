package com.scalablecapital.currencyapi.service;

import com.scalablecapital.currencyapi.dto.CurrencyConversionDto;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CurrencyService {

    @NotNull
    ReferenceRateDto getCurrenciesReferenceRate(@NotNull String source, @NotNull String target);

    @NotNull
    List<Currency> getAllCurrencies();

    @NotNull
    CurrencyConversionDto getCurrencyConversion(@NotNull String source, double sourceAmount, @NotNull String target);
}
