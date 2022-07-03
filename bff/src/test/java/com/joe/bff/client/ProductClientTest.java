package com.joe.bff.client;

import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.joe.bff.client.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "producer_service_api", port = "8888")
@SpringBootTest("feign.client.config.producer-service-api.url: localhost:8888")
class ProductClientTest {

    @Autowired
    private ProductClient productClient;

    @Pact(provider = "producer_service_api", consumer = "bff_service_api")
    public V4Pact getProductByIdPact(PactBuilder builder) {
        return builder
                .usingLegacyDsl()
                .given("provider gives a product with requested id")
                .uponReceiving("a request to GET a person")
                .path("/products/7")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type", "application/json")
                .body(new PactDslJsonBody()
                        .integerType("id", 7)
                        .stringValue("name", "Soap")
                        .stringValue("description", "Germ Protection Bathing Soap Bar")
                )
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getProductByIdPact")
    public void verifyOkResponse() {
        Product product = productClient.getProduct(7);
        assertEquals(7, product.getId());
        assertEquals("Soap", product.getName());
        assertEquals("Germ Protection Bathing Soap Bar", product.getDescription());
    }
}