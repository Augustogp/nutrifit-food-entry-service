package com.nutrifit.food_entry.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("nutrifit.food-log.grpc")
@Component
@Data
public class FoodLogGrpcServiceProperties {

    private String host;

    private Integer port;
}
