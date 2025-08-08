package com.nutrifit.food_entry.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private UUID foodLogId;

    @NotNull
    private UUID foodItemId;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull
    @Positive
    private Double grams;

    @NotNull
    @PositiveOrZero(message = "Calories must be zero or positive")
    private Double calories;

    @NotNull
    @PositiveOrZero(message = "Proteins must be zero or positive")
    private Double proteins;

    @NotNull
    @PositiveOrZero(message = "Carbohydrates must be zero or positive")
    private Double carbohydrates;

    @NotNull
    @PositiveOrZero(message = "Saturated fats must be zero or positive")
    private Double saturatedFats;

    @NotNull
    @PositiveOrZero(message = "Trans fats must be zero or positive")
    private Double transFats;

    @NotNull
    @PositiveOrZero(message = "Unsaturated fats must be zero or positive")
    private Double unsaturatedFats;

}
