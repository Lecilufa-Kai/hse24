package com.hse24.api.controller;

import com.hse24.api.dto.ProductDto;
import com.hse24.api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/hse24")
public class ProductController {

    private final ProductService productService;

    //Constructor injection don't need @Autowired, inject dependencies by spring boot
    public ProductController(
            ProductService productService
    ) {
        this.productService = productService;
    }


    @PostMapping(value = "products", produces = {"application/json"})
    public ResponseEntity<ProductDto> addCategory(
         @RequestBody ProductDto productDto
    ) {
        return ResponseEntity.ok().body(productService.addProduct(productDto));
    }


}
