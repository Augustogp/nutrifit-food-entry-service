package com.nutrifit.food_entry.service;

import com.nutrifit.food_entry.adapter.grpc.client.FoodItemGrpcService;
import com.nutrifit.food_entry.adapter.grpc.client.FoodLogGrpcService;
import com.nutrifit.food_entry.dto.FoodEntryRequest;
import com.nutrifit.food_entry.dto.FoodEntryResponse;
import com.nutrifit.food_entry.dto.FoodItemGrpcDto;
import com.nutrifit.food_entry.exception.ResourceNotFoundException;
import com.nutrifit.food_entry.mapper.FoodEntryMapper;
import com.nutrifit.food_entry.mapper.FoodItemGrpcDtoMapper;
import com.nutrifit.food_entry.model.FoodEntry;
import com.nutrifit.food_entry.repository.FoodEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodEntryService {

    private final FoodEntryRepository foodEntryRepository;

    private final FoodLogGrpcService foodLogGrpcService;
    private final FoodItemGrpcService foodItemGrpcService;

    public FoodEntryResponse findById(UUID id) {

        log.info("Finding food entry with id: {}", id);

        return foodEntryRepository.findById(id)
                .map(FoodEntryMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Food entry with id " + id + " not found"));
    }

    public List<FoodEntryResponse> findByFoodLogId(UUID foodLogId) {

        log.info("Finding food entries for food log with id: {}", foodLogId);

        return foodEntryRepository.findAllByFoodLogId(foodLogId)
                .stream()
                .map(FoodEntryMapper::toResponse)
                .toList();
    }

    public FoodEntryResponse createFoodEntryForFoodLog(UUID foodLogId, FoodEntryRequest foodEntryRequest) {

        foodLogGrpcService.getFoodLogById(foodLogId);

        FoodItemGrpcDto foodItem = foodItemGrpcService.getFoodItemByName(foodEntryRequest.name());

        FoodEntry foodEntry = FoodItemGrpcDtoMapper.toFoodEntry(foodItem, foodEntryRequest);

        foodEntry.setFoodLogId(foodLogId);

        log.info("Creating food entry for food log with id: {}", foodLogId);

        FoodEntry savedFoodEntry = foodEntryRepository.save(foodEntry);

        return FoodEntryMapper.toResponse(savedFoodEntry);
    }

    public FoodEntryResponse updateFoodEntry(UUID foodEntryId, FoodEntryRequest foodEntryRequest) {

        FoodEntry savedFoodEntry = foodEntryRepository.findById(foodEntryId)
                .orElseThrow(() -> new ResourceNotFoundException("Food entry with id " + foodEntryId + " not found"));

        log.info("Updating food entry with id: {}", foodEntryId);

        FoodItemGrpcDto foodItem = foodItemGrpcService.getFoodItemByName(foodEntryRequest.name());

        FoodEntry updatedFoodEntry = FoodItemGrpcDtoMapper.toFoodEntry(foodItem, foodEntryRequest);
        updatedFoodEntry.setId(savedFoodEntry.getId());
        updatedFoodEntry.setFoodLogId(savedFoodEntry.getFoodLogId());

        return FoodEntryMapper.toResponse(foodEntryRepository.save(updatedFoodEntry));
    }

    public void deleteFoodEntry(UUID foodEntryId) {

        if (!foodEntryRepository.existsById(foodEntryId)) {
            throw new ResourceNotFoundException("Food entry with id " + foodEntryId + " not found");
        }

        log.info("Deleting food entry with id: {}", foodEntryId);

        foodEntryRepository.deleteById(foodEntryId);
    }

}
