package com.nutrifit.food_entry;

import com.nutrifit.food_entry.adapter.grpc.client.FoodItemGrpcService;
import com.nutrifit.food_entry.adapter.grpc.client.FoodLogGrpcService;
import com.nutrifit.food_entry.dto.FoodItemGrpcDto;
import com.nutrifit.foodlog.v1.GetFoodLogByIdRequest;
import com.nutrifit.foodlog.v1.GrpcFoodLogResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;

@Import({TestcontainersConfiguration.class, GrpcTestConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Sql(scripts = "/testdata/mysql/create_food_entry_table_and_insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class FoodEntryServiceApplicationTests {

	@Autowired
	private FoodLogGrpcService foodLogGrpcService;

	@Autowired
	private FoodItemGrpcService foodItemGrpcService;

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {

		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

		Mockito.reset(foodLogGrpcService, foodItemGrpcService);
	}

	@Test
	void shouldReturnFoodEntryForValidFoodEntryId() {

		String id = "e1c6a214-7d7e-4d29-a9a9-58d6e1b623e0";

		RestAssured.given()
				.contentType(ContentType.JSON)
			.when()
				.get("/api/food-entries/{foodEntryId}" , id)
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("id", Matchers.equalTo(id));
	}

	@Test
	void shouldReturnNotFoundForInvalidFoodEntryId() {
		String id = "00000000-0000-0000-0000-000000000000";

		RestAssured.given()
				.contentType(ContentType.JSON)
			.when()
				.get("/api/food-entries/{foodEntryId}" , id)
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void shouldReturnValidListOfFoodEntriesForValidFoodLogId() {

		UUID foodLogId = UUID.fromString("d1c6a214-7d7e-4d29-a9a9-58d6e1b623e0");

		RestAssured.given()
				.contentType(ContentType.JSON)
			.when()
				.get("/api/food-entries/food-log/{foodLogId}", foodLogId)
			.then()
				.statusCode(HttpStatus.OK.value())
				.body(Matchers.notNullValue());
	}

	@Test
	void shouldReturnEmptyListForInvalidFoodLogId() {

		UUID foodLogId = UUID.fromString("00000000-0000-0000-0000-000000000000");

		RestAssured.given()
				.contentType(ContentType.JSON)
			.when()
				.get("/api/food-entries/food-log/{foodLogId}", foodLogId)
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("", Matchers.hasSize(0));
	}

	@Test
	void shouldCreateFoodEntryForValidFoodLogId() {

		String foodLogId = "d1c6a214-7d7e-4d29-a9a9-58d6e1b623e0";
		String foodItemName = "Apple";
		String foodItemId = "a1c6a214-7d7e-4d29-a9a9-58d6e1b623e0";
		Double grams = 150.0;

		when(foodLogGrpcService.getFoodLogById(Mockito.any()))
				.thenReturn(GrpcFoodLogResponse.newBuilder()
						.setFoodLogId(foodLogId)
						.setUserId("123e4567-e89b-12d3-a456-426614174001")
						.setDate("2023-10-01")
						.build());

		when(foodItemGrpcService.getFoodItemByName(foodItemName))
				.thenReturn(FoodItemGrpcDto.builder()
						.foodItemId(UUID.fromString(foodItemId))
						.name(foodItemName)
						.caloriesPerGram(0.52)
						.proteinPerGram(0.03)
						.carbsPerGram(0.14)
						.saturatedFatPerGram(0.01)
						.unsaturatedFatPerGram(0.01)
						.transFatPerGram(0.01)
						.build());

		var newFoodEntry = Map.of(
				"name", foodItemName,
				"foodLogId", foodLogId,
				"grams", grams
		);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(newFoodEntry)
			.when()
				.post("/api/food-entries/food-log/{foodLogId}", foodLogId)
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.body("name", Matchers.equalTo("Apple"))
				.body("foodLogId", Matchers.equalTo(foodLogId))
				.body("foodItemId", Matchers.equalTo(foodItemId))
				.body("grams", Matchers.comparesEqualTo(grams.floatValue()));
	}

	@Test
	void shouldReturnBadRequestForInvalidFoodEntryCreation() {

		String foodLogId = "d1c6a214-7d7e-4d29-a9a9-58d6e1b623e0";

		var invalidFoodEntry = Map.of(
				"name", "",
				"foodLogId", foodLogId,
				"grams", -100.0
		);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(invalidFoodEntry)
			.when()
				.post("/api/food-entries/food-log/{foodLogId}", foodLogId)
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void shouldUpdateFoodEntryForValidFoodEntryId() {

		String foodEntryId = "e1c6a214-7d7e-4d29-a9a9-58d6e1b623e0";
		String foodItemName = "Apple";
		String foodItemId = "a1c6a214-7d7e-4d29-a9a9-58d6e1b623e0";
		Double updatedGrams = 200.0;

		when(foodItemGrpcService.getFoodItemByName(foodItemName))
				.thenReturn(FoodItemGrpcDto.builder()
						.foodItemId(UUID.fromString(foodItemId))
						.name(foodItemName)
						.caloriesPerGram(0.52)
						.proteinPerGram(0.03)
						.carbsPerGram(0.14)
						.saturatedFatPerGram(0.01)
						.unsaturatedFatPerGram(0.01)
						.transFatPerGram(0.01)
						.build());

		var updatedFoodEntry = Map.of(
				"name", foodItemName,
				"foodLogId", "d1c6a214-7d7e-4d29-a9a9-58d6e1b623e0",
				"grams", updatedGrams
		);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(updatedFoodEntry)
			.when()
				.put("/api/food-entries/{foodEntryId}", foodEntryId)
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("id", Matchers.equalTo(foodEntryId))
				.body("name", Matchers.equalTo(foodItemName))
				.body("grams", Matchers.comparesEqualTo(updatedGrams.floatValue()));
	}

	@Test
	void shouldReturnNotFoundForInvalidFoodEntryUpdate() {

		String foodEntryId = "00000000-0000-0000-0000-000000000000";

		var updatedFoodEntry = Map.of(
				"name", "Apple",
				"foodLogId", "d1c6a214-7d7e-4d29-a9a9-58d6e1b623e0",
				"grams", 150.0
		);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(updatedFoodEntry)
			.when()
				.put("/api/food-entries/{foodEntryId}", foodEntryId)
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void shouldDeleteFoodEntryForValidFoodEntryId() {

		String foodEntryId = "e1c6a214-7d7e-4d29-a9a9-58d6e1b623e0";

		RestAssured.given()
				.contentType(ContentType.JSON)
			.when()
				.delete("/api/food-entries/{foodEntryId}", foodEntryId)
			.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
	}

	@Test
	void shouldNotDeleteFoodEntryForInvalidFoodEntryId() {

		String foodEntryId = "00000000-0000-0000-0000-000000000000";

		RestAssured.given()
				.contentType(ContentType.JSON)
			.when()
				.delete("/api/food-entries/{foodEntryId}", foodEntryId)
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
}
