package com.hse24.api.service;

import com.hse24.api.dto.ProductDto;

public interface ProductService {

    void deleteById(Long id);

    ProductDto addProduct(ProductDto productDto);
}
