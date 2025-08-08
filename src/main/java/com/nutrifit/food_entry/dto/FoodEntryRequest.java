package com.nutrifit.food_entry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FoodEntryRequest(

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotNull(message = "Must specify food entry grams")
        Double grams

) {}
