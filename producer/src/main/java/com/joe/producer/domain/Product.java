package com.joe.producer.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Product {
    Integer id;
    String name;
    String description;
    List<String> tags;
}
