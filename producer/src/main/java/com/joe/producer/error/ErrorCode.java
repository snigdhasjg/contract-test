package com.joe.producer.error;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404, "JOE-001"),
    INTERNAL_SERVER_ERROR(500, "JOE-002");

    int httpStatusCode;
    @JsonValue
    String code;
}
