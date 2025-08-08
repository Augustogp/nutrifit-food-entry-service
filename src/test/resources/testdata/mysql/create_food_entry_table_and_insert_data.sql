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

INSERT INTO food_entry (
    id, name, food_log_id, food_item_id, grams, calories, proteins, carbohydrates, saturated_fats, unsaturated_fats, trans_fats
) VALUES
      (UUID_TO_BIN('e1c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), 'Apple',      UUID_TO_BIN('d1c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), UUID_TO_BIN('a1c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), 150.0, 52.5, 0.3, 14.0, 0.1, 0.2, 0.0),
      (UUID_TO_BIN('e2c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), 'Banana',     UUID_TO_BIN('d1c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), UUID_TO_BIN('a2c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), 120.0, 105.0, 1.3, 27.0, 0.3, 0.4, 0.0),
      (UUID_TO_BIN('e3c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), 'Orange',     UUID_TO_BIN('d2c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), UUID_TO_BIN('a3c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), 130.0, 62.0, 1.2, 15.0, 0.1, 0.3, 0.0),
      (UUID_TO_BIN('e4c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), 'Strawberry', UUID_TO_BIN('d3c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), UUID_TO_BIN('a4c6a214-7d7e-4d29-a9a9-58d6e1b623e0'), 100.0, 32.0, 0.7, 7.7,  0.0, 0.1, 0.0);
