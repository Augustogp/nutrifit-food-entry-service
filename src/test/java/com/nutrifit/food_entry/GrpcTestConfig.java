package com.nutrifit.food_entry;

import com.nutrifit.food_entry.adapter.grpc.client.FoodItemGrpcService;
import com.nutrifit.food_entry.adapter.grpc.client.FoodLogGrpcService;
import com.nutrifit.food_entry.service.FoodEntryService;
import org.mockito.Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GrpcTestConfig {

    @Bean
    public FoodLogGrpcService foodLogGrpcService() {
        return org.mockito.Mockito.mock(FoodLogGrpcService.class);
    }

    @Bean
    public FoodItemGrpcService foodItemGrpcService() {
        return org.mockito.Mockito.mock(FoodItemGrpcService.class);
    }

}
