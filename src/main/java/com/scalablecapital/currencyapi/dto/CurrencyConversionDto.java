package com.scalablecapital.currencyapi.dto;

public class CurrencyConversionDto {

    private String source;
    private Double sourceAmount;
    private String target;
    private Double targetAmount;

    public String getSource() {
        return source;
    }

    public CurrencyConversionDto setSource(String source) {
        this.source = source;
        return this;
    }

    public Double getSourceAmount() {
        return sourceAmount;
    }

    public CurrencyConversionDto setSourceAmount(Double sourceAmount) {
        this.sourceAmount = sourceAmount;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public CurrencyConversionDto setTarget(String target) {
        this.target = target;
        return this;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public CurrencyConversionDto setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
        return this;
    }
}
