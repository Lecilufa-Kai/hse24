package com.hse24.api.service.impl;

import com.hse24.api.dto.ProductDto;
import com.hse24.api.entity.Category;
import com.hse24.api.entity.Product;
import com.hse24.api.repository.CategoryRepository;
import com.hse24.api.repository.ProductRepository;
import com.hse24.api.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
