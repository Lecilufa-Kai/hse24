package com.scalablecapital.currencyapi.entity;


import javax.validation.constraints.NotNull;

public class Currency {

    private final int id;
    @NotNull
    private final String name;
    @NotNull
    private final String abbreviation;
    private final double weight;
    private long visits;

    public Currency(int id, @NotNull String name, @NotNull String abbreviation, double weight, long visits) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.weight = weight;
        this.visits = visits;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public double getWeight() {
        return weight;
    }

    public long getVisits() {
        return visits;
    }

    public Currency setVisits(long visits) {
        this.visits = visits;
        return this;
    }
}
