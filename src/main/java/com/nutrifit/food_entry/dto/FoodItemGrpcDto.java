package com.nutrifit.food_entry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FoodItemGrpcDto(

        @NotNull(message = "Food item ID must not be null")
        UUID foodItemId,
        @NotBlank(message = "Food item name must not be blank")
        String name,
        @NotNull(message = "Calories per gram must not be null")
        @PositiveOrZero (message = "Calories per gram must be zero or positive")
        Double caloriesPerGram,
        @NotNull(message = "Protein per gram must not be null")
        @PositiveOrZero (message = "Protein per gram must be zero or positive")
        Double proteinPerGram,
        @NotNull(message = "Carbs per gram must not be null")
        @PositiveOrZero (message = "Carbs per gram must be zero or positive")
        Double carbsPerGram,
        @NotNull(message = "Saturated fat per gram must not be null")
        @PositiveOrZero (message = "Saturated fat per gram must be zero or positive")
        Double saturatedFatPerGram,
        @NotNull(message = "Unsaturated fat per gram must not be null")
        @PositiveOrZero (message = "Unsaturated fat per gram must be zero or positive")
        Double unsaturatedFatPerGram,
        @NotNull(message = "Trans fat per gram must not be null")
        @PositiveOrZero (message = "Trans fat per gram must be zero or positive")
        Double transFatPerGram
) {
}
