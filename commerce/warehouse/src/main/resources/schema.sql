CREATE SCHEMA IF NOT EXISTS commerce_warehouse;

DROP TABLE IF EXISTS commerce_warehouse.products;

CREATE TABLE IF NOT EXISTS commerce_warehouse.products (
    product_id VARCHAR(255) PRIMARY KEY,
    fragile BOOLEAN NOT NULL,
    quantity INT NOT NULL,
    weight REAL NOT NULL,
    dimension_width REAL NOT NULL,
    dimension_height REAL NOT NULL,
    dimension_depth REAL NOT NULL
);
