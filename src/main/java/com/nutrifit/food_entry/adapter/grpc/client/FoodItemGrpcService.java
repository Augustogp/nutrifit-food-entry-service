package com.nutrifit.food_entry.adapter.grpc.client;

import com.nutrifit.food_entry.config.FoodItemGrpcServiceProperties;
import com.nutrifit.food_entry.dto.FoodItemGrpcDto;
import com.nutrifit.food_entry.mapper.FoodItemGrpcDtoMapper;
import com.nutrifit.fooditem.v1.FoodItemServiceGrpc;
import com.nutrifit.fooditem.v1.GetFoodItemResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodItemGrpcService extends FoodItemServiceGrpc.FoodItemServiceImplBase {

    private final GrpcCallExceptionHandler grpcCallExceptionHandler;
    private final FoodItemGrpcServiceProperties foodItemGrpcServiceProperties;
    private FoodItemServiceGrpc.FoodItemServiceBlockingStub foodItemServiceBlockingStub;
    private ManagedChannel channel;

    @PostConstruct
    public void init() {

        log.info("Initializing FoodItem gRPC service client with address {}:{}", foodItemGrpcServiceProperties.getHost(), foodItemGrpcServiceProperties.getPort());
        this.channel = ManagedChannelBuilder.forAddress(foodItemGrpcServiceProperties.getHost(), foodItemGrpcServiceProperties.getPort())
                .usePlaintext()
                .build();
        this.foodItemServiceBlockingStub = FoodItemServiceGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        if (channel != null) {
            log.info("Shutting down gRPC FoodItemGrpcService stub");
            channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
        } else {
            log.warn("Failed to shutdown gRPC FoodLogGrpcService stub");
            throw new IllegalStateException("gRPC channel is already null");
        }
    }

    public FoodItemGrpcDto getFoodItemByName(String foodItemName) {

        log.info("Calling gRPC FoodItemService for foodItemId: {}", foodItemName);

        GetFoodItemResponse getFoodItemResponse = grpcCallExceptionHandler.handle("FoodItem", foodItemName, () ->
                foodItemServiceBlockingStub.getFoodItemByName(
                        com.nutrifit.fooditem.v1.GetFoodItemByNameRequest.newBuilder()
                                .setName(foodItemName)
                                .build()
                )
        );

        return FoodItemGrpcDtoMapper.toFoodItemGrpcDto(getFoodItemResponse);
    }

}
