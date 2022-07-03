package com.joe.bff.client;

import com.joe.bff.client.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producer-service-api", url = "${feign.client.config.producer-service-api.url}", path = "/products")
public interface ProductClient {

    @GetMapping("/{id}")
    Product getProduct(@PathVariable Integer id);
}
