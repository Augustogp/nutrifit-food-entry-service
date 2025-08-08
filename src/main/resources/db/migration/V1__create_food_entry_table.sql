CREATE TABLE IF NOT EXISTS food_entry (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    food_log_id BINARY(16) NOT NULL,
    food_item_id BINARY(16) NOT NULL,
    grams DECIMAL(8, 5) NOT NULL,
    calories DECIMAL(8, 5) NOT NULL,
    proteins DECIMAL(8, 5) NOT NULL,
    carbohydrates DECIMAL(8, 5) NOT NULL,
    saturated_fats DECIMAL(8, 5) NOT NULL,
    unsaturated_fats DECIMAL(8, 5) NOT NULL,
    trans_fats DECIMAL(8, 5) NOT NULL
);