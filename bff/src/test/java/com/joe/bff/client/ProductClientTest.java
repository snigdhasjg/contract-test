package com.joe.bff.client;

import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.joe.bff.client.model.Product;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "producer_service_api", port = "8888")
@SpringBootTest(classes = ProductClient.class, properties = "feign.client.config.producer-service-api.url=localhost:8888")
@EnableFeignClients(clients = ProductClient.class)
@EnableAutoConfiguration
class ProductClientTest {

    @Autowired
    private ProductClient productClient;

    @Pact(provider = "producer_service_api", consumer = "bff_service_api")
    public V4Pact productByIdPact(PactBuilder builder) {
        return builder
                .usingLegacyDsl()
                .given("validRequest")
                .uponReceiving("A valid request to GET a product")
                .matchPath("/products/\\d+", "/products/7")
                .method("GET")
                .willRespondWith()
                .status(200)
                .matchHeader("Content-Type", "application/json", "application/json")
                .body(new PactDslJsonBody()
                        .integerType("id", 7)
                        .stringType("name", "Soap")
                        .stringType("rating", "4*")
                        .stringType("description", "Germ Protection Bathing Soap Bar")
                )
                .toPact(V4Pact.class);
    }

    @Pact(provider = "producer_service_api", consumer = "bff_service_api")
    public V4Pact productNotFoundPact(PactBuilder builder) {
        return builder
                .usingLegacyDsl()
                .given("invalidRequest")
                .uponReceiving("An invalid request of a product that doesn't exists")
                .matchPath("/products/\\d+", "/products/7")
                .method("GET")
                .willRespondWith()
                .status(404)
                .matchHeader("Content-Type", "application/json", "application/json")
                .body(new PactDslJsonBody()
                        .stringType("code", "API-404")
                        .stringType("message", "Product with id 7 not found")
                )
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "productByIdPact")
    public void verifyOkResponse() {
        Product product = productClient.getProduct(7);
        assertEquals(7, product.getId());
        assertEquals("Soap", product.getName());
        assertEquals("Germ Protection Bathing Soap Bar", product.getDescription());
    }

    @Test
    @PactTestFor(pactMethod = "productNotFoundPact")
    public void verifyNotFoundResponse() {
        assertThrows(FeignException.NotFound.class, () -> productClient.getProduct(8));
    }
}