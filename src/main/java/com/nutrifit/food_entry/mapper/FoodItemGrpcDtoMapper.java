package com.nutrifit.food_entry.mapper;

import com.nutrifit.food_entry.dto.FoodEntryRequest;
import com.nutrifit.food_entry.dto.FoodItemGrpcDto;
import com.nutrifit.food_entry.model.FoodEntry;
import com.nutrifit.fooditem.v1.GetFoodItemResponse;

import java.util.UUID;

public class FoodItemGrpcDtoMapper {

    public static FoodItemGrpcDto toFoodItemGrpcDto(GetFoodItemResponse getFoodItemResponse) {

        return FoodItemGrpcDto.builder()
                .foodItemId(UUID.fromString(getFoodItemResponse.getId()))
                .name(getFoodItemResponse.getName())
                .caloriesPerGram(Double.valueOf(getFoodItemResponse.getCaloriesPerGram()))
                .proteinPerGram(Double.valueOf(getFoodItemResponse.getProteinsPerGram()))
                .carbsPerGram(Double.valueOf(getFoodItemResponse.getCarbohydratesPerGram()))
                .saturatedFatPerGram(Double.valueOf(getFoodItemResponse.getSaturatedFatsPerGram()))
                .unsaturatedFatPerGram(Double.valueOf(getFoodItemResponse.getUnsaturatedFatsPerGram()))
                .transFatPerGram(Double.valueOf(getFoodItemResponse.getTransFatsPerGram()))
                .build();
    }

    public static FoodEntry toFoodEntry(FoodItemGrpcDto foodItemGrpcDto, FoodEntryRequest foodEntryRequest) {

        return FoodEntry.builder()
                .foodItemId(foodItemGrpcDto.foodItemId())
                .name(foodItemGrpcDto.name())
                .grams(foodEntryRequest.grams())
                .calories(foodEntryRequest.grams() * foodItemGrpcDto.caloriesPerGram())
                .proteins(foodEntryRequest.grams() * foodItemGrpcDto.proteinPerGram())
                .carbohydrates(foodEntryRequest.grams() * foodItemGrpcDto.carbsPerGram())
                .saturatedFats(foodEntryRequest.grams() * foodItemGrpcDto.saturatedFatPerGram())
                .unsaturatedFats(foodEntryRequest.grams() * foodItemGrpcDto.unsaturatedFatPerGram())
                .transFats(foodEntryRequest.grams() * foodItemGrpcDto.transFatPerGram())
                .build();
    }

}
