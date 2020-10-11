package com.scalablecapital.currencyapi.dto;

public class ReferenceRateDto {

    private String currencyPair;
    private Double referenceRate;
    private String link;

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public Double getReferenceRate() {
        return referenceRate;
    }

    public void setReferenceRate(Double referenceRate) {
        this.referenceRate = referenceRate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
