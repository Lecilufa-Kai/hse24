package com.scalablecapital.currencyapi.repository;

import com.scalablecapital.currencyapi.entity.Currency;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CurrencyRepository {
    
    @NotNull
    Currency findCurrency(@NotNull String abbreviation);

    void updateCurrencyVisits(@NotNull Currency currency);

    @NotNull
    List<Currency> getAllCurrencies();
}
