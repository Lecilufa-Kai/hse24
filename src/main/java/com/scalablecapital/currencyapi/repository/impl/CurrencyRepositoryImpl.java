package com.scalablecapital.currencyapi.repository.impl;

import com.scalablecapital.currencyapi.repository.CurrencyRepository;
import com.scalablecapital.currencyapi.db.DataHolder;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.exception.CurrencyNotFoundException;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository {

    //to limit the thread amount when there are too many requests coming at the same time
    private final ExecutorService executor = Executors.newFixedThreadPool(100);

    @Override
    @NotNull
    public Currency findCurrency(String abbreviation) {

        Currency result = DataHolder.currencyDB.stream()
                .filter(currency -> currency.getAbbreviation().equals(abbreviation))
                .findAny().orElse(null);

        if (result == null) {
            throw new CurrencyNotFoundException("currency " + abbreviation + " not found");
        }
        return result;
    }

    @Override
    public void updateCurrencyVisits(final Currency currency) {
        executor.execute(() -> {
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
