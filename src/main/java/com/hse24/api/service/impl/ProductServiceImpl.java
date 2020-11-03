package com.hse24.api.service.impl;

import com.hse24.api.dto.CurrencyDto;
import com.hse24.api.entity.Category;
import com.hse24.api.entity.Product;
import com.hse24.api.repository.CategoryRepository;
import com.hse24.api.dto.ProductDto;
import com.hse24.api.repository.ProductRepository;
import com.hse24.api.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            ModelMapper modelMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {

//        RestTemplate restTemplate = new RestTemplate();
//        String currencyApiUrl
//                = "http://data.fixer.io/api/latest?access_key=bbac5b49d9915c7e9c7d64ed15618d79";
//        CurrencyDto currencyDto = restTemplate.getForObject(currencyApiUrl, CurrencyDto.class);
//        Double exchangeRate = currencyDto.getRates().get(productDto.getCurrency());


        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category != null) {
            Product product = modelMapper.map(productDto, Product.class);
            product.setCategory(category);
            product = productRepository.save(product);
            return modelMapper.map(product, ProductDto.class);
        }

        return null;
    }
}
