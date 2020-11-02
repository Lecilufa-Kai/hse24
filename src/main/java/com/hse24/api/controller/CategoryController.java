package com.hse24.api.controller;

import com.hse24.api.dto.CategoryDto;
import com.hse24.api.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/hse24")
public class CategoryController {

    private final CategoryService categoryService;

    //Constructor injection don't need @Autowired, inject dependencies by spring boot
    public CategoryController(
            CategoryService categoryService
    ) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "categories", produces = {"application/json"})
    public ResponseEntity<List<CategoryDto>> listCategories() throws Exception {

        return ResponseEntity.ok().body(categoryService.listCategories());
    }

    @PostMapping(value = "categories", produces = {"application/json"})
    public ResponseEntity<CategoryDto> addCategory(
         @RequestBody CategoryDto categoryDto
    ) {
        return ResponseEntity.ok().body(categoryService.addCategory(categoryDto));
    }

    @DeleteMapping(value = "categories/{id}", produces = {"application/json"})
    public ResponseEntity deleteCategory(
            @PathVariable Long id
    ) {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
