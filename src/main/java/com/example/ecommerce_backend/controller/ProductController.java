package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.ProductDto;
import com.example.ecommerce_backend.entity.Product;
import com.example.ecommerce_backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Save product
    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDto productDto) {
        Product savedProduct = productService.saveProduct(productDto);
        return ResponseEntity.ok(savedProduct);
    }

    // Get all products
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
