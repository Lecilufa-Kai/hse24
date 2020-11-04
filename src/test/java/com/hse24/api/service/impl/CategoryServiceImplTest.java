package com.hse24.api.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import com.hse24.api.dto.CategoryDto;
import com.hse24.api.entity.Category;
import com.hse24.api.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    public void testDeleteById() {

        long id = 1L;

        Category category = new Category();
        category.setId(id);
        Optional<Category> categoryOptional = Optional.of(category);

        Mockito.when(categoryRepository.findById(id)).thenReturn(categoryOptional);

        categoryService.deleteById(id);
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void addCategory() {

        long id = 1L;
        String name = "category1";

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(name);

        Category category = new Category();
        category.setId(id);
        category.setName(name);

        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        Mockito.when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDto newDto = categoryService.addCategory(categoryDto);

        assertNotNull(newDto);
        assertEquals(id, newDto.getId());
        assertEquals(name, newDto.getName());
    }
}