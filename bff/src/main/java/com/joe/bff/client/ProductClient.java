package com.joe.bff.client;

import com.joe.bff.client.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "product-api", url = "${feign.client.config.product-api.url}", path = "/api/products")
public interface ProductClient {

    @GetMapping("/{id}")
    Product getProduct(@PathVariable Integer id);
}
