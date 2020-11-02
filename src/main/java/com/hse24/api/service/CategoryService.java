package com.hse24.api.service;

import com.hse24.api.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> listCategories();

    void deleteById(Long id);

    CategoryDto addCategory(CategoryDto categoryDto);
}
