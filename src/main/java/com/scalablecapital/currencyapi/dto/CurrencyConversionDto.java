package com.scalablecapital.currencyapi.dto;

public class CurrencyConversionDto {

    private String source;
    private Double sourceAmount;
    private String target;
    private Double targetAmount;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(Double sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }
}
