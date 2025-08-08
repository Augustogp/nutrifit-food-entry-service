package com.nutrifit.food_entry;

import org.springframework.boot.SpringApplication;

public class TestFoodEntryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(FoodEntryServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
