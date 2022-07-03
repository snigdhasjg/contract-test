package com.joe.producer.repository;

import com.joe.producer.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductRepository {

    private final Map<Integer, Product> products;

    public ProductRepository() {
        this.products = Map.of(
                1, Product
                        .builder()
                        .id(1)
                        .name("Soap")
                        .description("Germ Protection Bathing Soap Bar")
                        .tags(List.of("Hand", "Body", "Clean", "Refreshing"))
                        .build(),
                2, Product
                        .builder()
                        .id(2)
                        .name("T-Shirt")
                        .description("Regular unisex T-Shirt")
                        .build()
        );
    }

    public Optional<Product> fetchProduct(Integer id) {
        return Optional.ofNullable(products.get(id));
    }
}
