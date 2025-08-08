package com.nutrifit.food_entry.adapter.grpc.client;

import com.nutrifit.food_entry.exception.ExternalServiceException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GrpcCallExceptionHandler {

    public <T> T handle(String context, String value, GrpcCall<T> grpcCall) {
        try {
            return grpcCall.run();
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new com.nutrifit.food_entry.exception.ResourceNotFoundException("Resource not found for " + context + " with id: " + value);
            } else if (e.getStatus().getCode() == Status.Code.INVALID_ARGUMENT) {
                throw new IllegalArgumentException("Invalid argument provided for " + context + " with id: " + value, e);
            } else {
                throw new ExternalServiceException("gRPC error in " + context + ": " + e.getStatus(), e);
            }
        }
    }


    public interface GrpcCall<T> {
        T run() throws RuntimeException;
    }
}
