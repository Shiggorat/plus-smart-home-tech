CREATE SCHEMA IF NOT EXISTS commerce_store;

DROP TABLE IF EXISTS commerce_store.products;

CREATE TABLE IF NOT EXISTS commerce_store.products (
    product_id VARCHAR(255) PRIMARY KEY NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    description TEXT,
    image_src VARCHAR(500),
    quantity_state VARCHAR(50) NOT NULL,
    product_state VARCHAR(50) NOT NULL,
    product_category VARCHAR(50),
    price NUMERIC(19, 2) NOT NULL
);
