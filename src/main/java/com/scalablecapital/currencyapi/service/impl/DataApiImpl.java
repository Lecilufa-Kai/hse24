package com.scalablecapital.currencyapi.service.impl;

import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.service.DataApi;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DataApiImpl implements DataApi {

    //to limit the thread amount when there are too many requests coming at the same time
    private final ExecutorService executor = Executors.newFixedThreadPool(100);

    @Override
    public void updateCurrencyVisits(final Currency currency){
        executor.execute(() -> updateVisits(currency));
    }

    private void updateVisits(Currency currency) {
        synchronized (currency){
            currency.setVisits(currency.getVisits() + 1);
        }
    }
}
