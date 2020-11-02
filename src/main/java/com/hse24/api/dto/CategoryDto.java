package com.hse24.api.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    private long id;
    private String name;
    private List<ProductDto> products = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
