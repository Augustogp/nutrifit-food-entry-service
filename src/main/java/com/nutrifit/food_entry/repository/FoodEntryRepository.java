package com.nutrifit.food_entry.repository;

import com.nutrifit.food_entry.model.FoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface FoodEntryRepository extends JpaRepository<FoodEntry, UUID> {

    List<FoodEntry> findAllByFoodLogId(UUID foodLogId);
}
