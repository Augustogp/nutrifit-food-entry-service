package com.nutrifit.food_entry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FoodEntryResponse(

        @NotNull(message = "Id cannot be null")
        UUID id,

        @NotNull
        UUID foodLogId,

        @NotBlank
        String name,

        @NotNull
        UUID foodItemId,

        @NotNull
        Double calories,

        @NotNull
        Double grams,

        @NotNull
        Double proteins,

        @NotNull
        Double carbohydrates,

        @NotNull
        Double saturatedFats,

        @NotNull
        Double transFats,

        @NotNull
        Double unsaturatedFats) {}
