package com.hse24.api.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import com.hse24.api.dto.ProductDto;
import com.hse24.api.entity.Category;
import com.hse24.api.entity.Product;
import com.hse24.api.repository.CategoryRepository;
import com.hse24.api.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    public void deleteById() {

        long id = 1L;
        productService.deleteById(id);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void addProduct() {

        long productId = 1L;
        long categoryId = 2L;
        String productName = "product1";
        String categoryName = "categoryName";

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setName(productName);
        productDto.setCategoryId(categoryId);

        Product product = new Product();
        product.setId(productId);
        product.setName(productName);

        Optional<Category> categoryOptional = Optional.of(category);

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(categoryOptional);
        Mockito.when(modelMapper.map(productDto, Product.class)).thenReturn(product);
        Mockito.when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);
        Mockito.when(productRepository.save(product)).thenReturn(product);

        ProductDto newProductDto = productService.addProduct(productDto);

        assertNotNull(newProductDto);
        assertEquals(productId, newProductDto.getId());
        assertEquals(productName, newProductDto.getName());
    }
}