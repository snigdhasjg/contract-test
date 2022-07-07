package com.joe.producer.error;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError {
    ErrorCode code;
    String message;
}
