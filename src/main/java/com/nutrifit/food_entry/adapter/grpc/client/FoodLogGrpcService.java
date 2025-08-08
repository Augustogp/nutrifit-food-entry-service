package com.nutrifit.food_entry.adapter.grpc.client;

import com.nutrifit.food_entry.config.FoodLogGrpcServiceProperties;
import com.nutrifit.foodlog.v1.FoodLogServiceGrpc;
import com.nutrifit.foodlog.v1.GrpcFoodLogResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodLogGrpcService {

    private ManagedChannel channel;
    private FoodLogServiceGrpc.FoodLogServiceBlockingStub foodLogServiceBlockingStub;
    private final FoodLogGrpcServiceProperties properties;
    private final GrpcCallExceptionHandler grpcCallExceptionHandler;

    @PostConstruct
    public void init() {

        log.info("Initializing FoodLog gRPC service client with address {}:{}", properties.getHost(), properties.getPort());
        channel = ManagedChannelBuilder.forAddress(properties.getHost(), properties.getPort())
                .usePlaintext()
                .build();

        foodLogServiceBlockingStub = FoodLogServiceGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {

        if (channel != null) {
            log.info("Shutting down gRPC FoodLogGrpcService stub");
            channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
        } else {
            log.warn("Failed to shutdown gRPC FoodLogGrpcService stub");
            throw new IllegalStateException("gRPC channel is already null");
        }
    }

    public GrpcFoodLogResponse getFoodLogById(UUID foodLogId) {

        log.info("Calling gRPC FoodLogService for foodLogId: {}", foodLogId);
        return grpcCallExceptionHandler.handle("FoodLog", foodLogId.toString(), () ->
            foodLogServiceBlockingStub.getFoodLogById(
                com.nutrifit.foodlog.v1.GetFoodLogByIdRequest.newBuilder()
                    .setFoodLogId(foodLogId.toString())
                    .build()
            )
        );
    }
}
