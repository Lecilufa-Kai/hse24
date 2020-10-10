package com.scalablecapital.currencyapi.dto;

public class ReferenceRateDto {

    private String currencyPair;
    private Double referenceRate;
    private String link;

    public String getCurrencyPair() {
        return currencyPair;
    }

    public ReferenceRateDto setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public Double getReferenceRate() {
        return referenceRate;
    }

    public ReferenceRateDto setReferenceRate(Double referenceRate) {
        this.referenceRate = referenceRate;
        return this;
    }

    public String getLink() {
        return link;
    }

    public ReferenceRateDto setLink(String link) {
        this.link = link;
        return this;
    }
}
