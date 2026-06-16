package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.ProductDto;
import com.example.ecommerce_backend.entity.Product;

import java.util.List;

public interface ProductService {


    Product saveProduct(ProductDto productDto);
    List<Product> getAllProducts();



}
