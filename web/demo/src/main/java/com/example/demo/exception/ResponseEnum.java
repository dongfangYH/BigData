package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum implements BusinessExceptionAssert {

    SUCCESS(0, "success."),
    RESOURCE_NOT_FOUND(1, "resource not found."),
    SERVER_ERROR(7000, "server error."),
    ARGUMENT_NOT_NULL(7001, "arguments should not be null."),
    ARGUMENT_NULL(7002, "null argument expected."),
    INVALID_ARGUMENT(7003, "invalid arguments."),
    NOT_EQUAL(7004, "arguments not equal."),
    ASSERT_EQUAL(7005, "arguments equal.")
    ;

    private int code;
    private String message;
}
