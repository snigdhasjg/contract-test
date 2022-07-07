package com.joe.producer.controller;

import com.joe.producer.domain.Product;
import com.joe.producer.error.ClientException;
import com.joe.producer.error.ErrorCode;
import com.joe.producer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        return productRepository.fetchProduct(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new ClientException(ErrorCode.NOT_FOUND, String.format("Product with id %d not found.", id))
                );
    }
}
