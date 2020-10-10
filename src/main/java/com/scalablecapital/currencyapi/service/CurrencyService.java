package com.scalablecapital.currencyapi.service;

import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CurrencyService {

    @NotNull
    ReferenceRateDto currenciesReferenceRate(@NotNull String source,@NotNull String target);

    @NotNull
    List<Currency> getAllCurrencies();
}
