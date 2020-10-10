package com.scalablecapital.currencyapi.service.impl;

import com.scalablecapital.currencyapi.db.DataHolder;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.exception.CurrencyNotFoundException;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;
import com.scalablecapital.currencyapi.service.CurrencyService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    //to limit the thread amount when there are too many requests coming at the same time
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    public @NotNull ReferenceRateDto currenciesReferenceRate(@NotNull String source, @NotNull String target) {
        Currency sourceCurrency = findCurrency(source);
        Currency targetCurrency = findCurrency(target);

        ReferenceRateDto referenceRateDto = new ReferenceRateDto();
        referenceRateDto.setCurrencyPair(source + "/" + target);
        referenceRateDto.setReferenceRate(sourceCurrency.getWeight()/targetCurrency.getWeight());
        referenceRateDto.setLink(source + "/" + target);

        updateCurrencyVisits(sourceCurrency);
        updateCurrencyVisits(targetCurrency);

        return referenceRateDto;
    }

    private void updateCurrencyVisits(final Currency currency){
        executor.execute(() -> updateVisits(currency));
    }

    private void updateVisits(Currency currency) {
        synchronized (currency){
            currency.setVisits(currency.getVisits() + 1);
        }
    }

    @NotNull
    private Currency findCurrency(String abbreviation){

        Currency result = DataHolder.currencyDB.stream()
                .filter(currency -> currency.getAbbreviation().equals(abbreviation))
                .findAny().orElse(null);

        if(result == null){
            throw  new CurrencyNotFoundException(abbreviation + " not found");
        }
        return result;
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return DataHolder.currencyDB;
    }
}
