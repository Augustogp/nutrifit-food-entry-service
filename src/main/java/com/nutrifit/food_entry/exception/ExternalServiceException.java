package com.nutrifit.food_entry.exception;

import io.grpc.StatusRuntimeException;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String s, StatusRuntimeException e) {
    }
}
