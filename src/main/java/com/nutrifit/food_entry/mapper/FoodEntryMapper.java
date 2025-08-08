package com.nutrifit.food_entry.mapper;

import com.nutrifit.food_entry.dto.FoodEntryRequest;
import com.nutrifit.food_entry.dto.FoodEntryResponse;
import com.nutrifit.food_entry.model.FoodEntry;

public class FoodEntryMapper {

    public static FoodEntryResponse toResponse(FoodEntry foodEntry) {
        return FoodEntryResponse.builder()
                .id(foodEntry.getId())
                .foodLogId(foodEntry.getFoodLogId())
                .name(foodEntry.getName())
                .foodItemId(foodEntry.getFoodItemId())
                .grams(foodEntry.getGrams())
                .calories(foodEntry.getCalories())
                .proteins(foodEntry.getProteins())
                .carbohydrates(foodEntry.getCarbohydrates())
                .saturatedFats(foodEntry.getSaturatedFats())
                .unsaturatedFats(foodEntry.getUnsaturatedFats())
                .transFats(foodEntry.getTransFats())
                .build();
    }

}
