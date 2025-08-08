package com.nutrifit.food_entry.controller;

import com.nutrifit.food_entry.dto.FoodEntryRequest;
import com.nutrifit.food_entry.dto.FoodEntryResponse;
import com.nutrifit.food_entry.service.FoodEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/food-entries")
@RequiredArgsConstructor
public class FoodEntryController {

    private final FoodEntryService foodEntryService;

    @GetMapping("{foodEntryId}")
    public ResponseEntity<FoodEntryResponse> getFoodEntryById(@PathVariable UUID foodEntryId) {

        FoodEntryResponse foodEntryResponse = foodEntryService.findById(foodEntryId);

        return ResponseEntity.ok(foodEntryResponse);
    }

    @GetMapping("/food-log/{foodLogId}")
    public ResponseEntity<List<FoodEntryResponse>> getFoodEntriesByFoodLogId(@PathVariable UUID foodLogId) {

        List<FoodEntryResponse> foodEntryResponseList = foodEntryService.findByFoodLogId(foodLogId);

        return ResponseEntity.ok(foodEntryResponseList);
    }

    @PostMapping("/food-log/{foodLogId}")
    public ResponseEntity<FoodEntryResponse> createFoodEntryForFoodLog(@PathVariable UUID foodLogId,
                                                       @Valid @RequestBody FoodEntryRequest foodEntryRequest) {

        FoodEntryResponse foodEntryResponse = foodEntryService.createFoodEntryForFoodLog(foodLogId, foodEntryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(foodEntryResponse);
    }

    @PutMapping("{foodEntryId}")
    public ResponseEntity<FoodEntryResponse> updateFoodEntry(@PathVariable UUID foodEntryId,
                                             @Valid @RequestBody FoodEntryRequest foodEntryRequest) {

        FoodEntryResponse foodEntryResponse = foodEntryService.updateFoodEntry(foodEntryId, foodEntryRequest);

        return ResponseEntity.ok(foodEntryResponse);
    }

    @DeleteMapping("{foodEntryId}")
    public ResponseEntity<Void> deleteFoodEntryById(@PathVariable UUID foodEntryId) {

        foodEntryService.deleteFoodEntry(foodEntryId);

        return ResponseEntity.noContent().build();
    }
}
