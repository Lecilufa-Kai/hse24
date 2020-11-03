package com.hse24.api.dto;

import java.io.Serializable;
import java.util.Map;

public class CurrencyDto implements Serializable {

    private Map<String,Double> rates;

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
