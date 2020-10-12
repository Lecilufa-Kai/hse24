package com.scalablecapital.currencyapi.service.impl;

import com.scalablecapital.currencyapi.repository.CurrencyRepository;
import com.scalablecapital.currencyapi.dto.CurrencyConversionDto;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.service.CurrencyService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    private final static String LINK_TEMPLATE = "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-%s.en.html";

    //Constructor injection don't need @Autowired, inject dependencies by spring boot
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public @NotNull ReferenceRateDto getCurrenciesReferenceRate(@NotNull String source, @NotNull String target) {
        Currency sourceCurrency = currencyRepository.findCurrency(source);
        Currency targetCurrency = currencyRepository.findCurrency(target);

        //use a thread to update visits,return immediately, not blocking main thread.
        currencyRepository.updateCurrencyVisits(sourceCurrency);
        currencyRepository.updateCurrencyVisits(targetCurrency);

        return buildReferenceRate(sourceCurrency, targetCurrency);
    }

    @NotNull
    private ReferenceRateDto buildReferenceRate(Currency sourceCurrency, Currency targetCurrency) {

        String currencyPair = sourceCurrency.getAbbreviation() + "/" +
                targetCurrency.getAbbreviation();

        String link = String.format(LINK_TEMPLATE, sourceCurrency.getAbbreviation().toLowerCase());

        BigDecimal sourceWeight = BigDecimal.valueOf(sourceCurrency.getWeight());
        BigDecimal targetWeight = BigDecimal.valueOf(targetCurrency.getWeight());
        Double referenceRate = sourceWeight.divide(targetWeight, 4, RoundingMode.HALF_UP).doubleValue();

        ReferenceRateDto referenceRateDto = new ReferenceRateDto();
        referenceRateDto.setCurrencyPair(currencyPair);
        referenceRateDto.setReferenceRate(referenceRate);
        referenceRateDto.setLink(link);

        return referenceRateDto;
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.getAllCurrencies();
    }

    @Override
    public CurrencyConversionDto getCurrencyConversion(@NotNull String source, double sourceAmount, @NotNull String target) {

        Currency sourceCurrency = currencyRepository.findCurrency(source);
        Currency targetCurrency = currencyRepository.findCurrency(target);

        //use a thread to update visits,return immediately, not blocking main thread.
        currencyRepository.updateCurrencyVisits(sourceCurrency);
        currencyRepository.updateCurrencyVisits(targetCurrency);

        return buildCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount);
    }

    private CurrencyConversionDto buildCurrencyConversion(Currency sourceCurrency, Currency targetCurrency, double sourceAmount) {

        BigDecimal sourceValue = BigDecimal.valueOf(sourceAmount);
        BigDecimal sourceWeight = BigDecimal.valueOf(sourceCurrency.getWeight());
        BigDecimal targetWeight = BigDecimal.valueOf(targetCurrency.getWeight());
        Double targetAmount = sourceValue.multiply(targetWeight)
                .divide(sourceWeight, 4, RoundingMode.HALF_UP).doubleValue();

        CurrencyConversionDto currencyConversionDto = new CurrencyConversionDto();
        currencyConversionDto.setSource(sourceCurrency.getAbbreviation());
        currencyConversionDto.setSourceAmount(sourceAmount);
        currencyConversionDto.setTarget(targetCurrency.getAbbreviation());
        currencyConversionDto.setTargetAmount(targetAmount);

        return currencyConversionDto;
    }


}
