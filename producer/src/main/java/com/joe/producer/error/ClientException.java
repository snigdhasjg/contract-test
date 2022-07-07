package com.joe.producer.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class ClientException extends RuntimeException {
    ErrorCode code;

    public ClientException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
}
