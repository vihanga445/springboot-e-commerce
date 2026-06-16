package com.example.ecommerce_backend.service.impl;

import com.example.ecommerce_backend.dto.ProductDto;
import com.example.ecommerce_backend.entity.Product;
import com.example.ecommerce_backend.repository.ProductRepository;
import com.example.ecommerce_backend.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(ProductDto productDto) {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


}
