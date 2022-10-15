package com.joe.producer.controller;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.joe.producer.domain.Product;
import com.joe.producer.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
@Provider("producer_service_api")
@PactBroker
class ProductControllerContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new MockMvcTestTarget(mockMvc));
    }

    @State(value = "shouldGiveProductForProductId")
    void shouldGiveProductForProductId() {
        Product soap = Product
                .builder()
                .id(7)
                .name("Soap")
                .description("Germ Protection Bathing Soap Bar")
                .tags(List.of("bath", "bubble"))
                .build();

        when(productRepository.fetchProduct(anyInt()))
                .thenReturn(Optional.of(soap));
    }

    @State(value = "shouldThrow404ErrorIfRequestedProductNotFound")
    void shouldThrow404ErrorIfRequestedProductNotFound() {
        when(productRepository.fetchProduct(anyInt()))
                .thenReturn(Optional.empty());
    }
}