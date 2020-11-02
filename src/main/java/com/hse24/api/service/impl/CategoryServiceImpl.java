package com.hse24.api.service.impl;

import com.hse24.api.dto.CategoryDto;
import com.hse24.api.dto.ProductDto;
import com.hse24.api.entity.Category;
import com.hse24.api.repository.CategoryRepository;
import com.hse24.api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            ModelMapper modelMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public List<CategoryDto> listCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> {
                    CategoryDto categoryDto = modelMapper.map(category,CategoryDto.class);

                    List<ProductDto> productDtos = category.getProducts()
                            .stream()
                            .map(product -> modelMapper.map(product, ProductDto.class))
                            .collect(Collectors.toList());

                    categoryDto.setProducts(productDtos);

                    return categoryDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()){
            categoryRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto,Category.class);
        category = categoryRepository.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }
}
