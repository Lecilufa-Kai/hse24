package com.scalablecapital.currencyapi.repository.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.validation.constraints.NotNull;

import com.scalablecapital.currencyapi.db.DataHolder;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.exception.CurrencyNotFoundException;
import com.scalablecapital.currencyapi.repository.CurrencyRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository {

    //to limit the thread amount when there are too many requests coming at the same time
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);

    @Override
    @NotNull
    public Currency findCurrency(String abbreviation) {

        return DataHolder.currencyDB.stream()
                                    .filter(currency -> currency.getAbbreviation().equals(abbreviation))
                                    .findAny()
                                    .orElseThrow(() -> new CurrencyNotFoundException("Currency " + abbreviation + " not found."));
    }

    @Override
    public void updateCurrencyVisits(final Currency currency) {
        fixedThreadPool.execute(() -> {
            synchronized (currency) {
                currency.setVisits(currency.getVisits() + 1);
            }
        });
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return DataHolder.currencyDB;
    }
}
