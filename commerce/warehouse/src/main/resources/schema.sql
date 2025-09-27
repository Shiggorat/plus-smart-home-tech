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

CREATE TABLE IF NOT EXISTS commerce_warehouse.order_booking (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id VARCHAR(255) NOT NULL,
    delivery_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS commerce_warehouse.order_booking_products (
    order_booking_id BIGINT NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (order_booking_id, product_id),
    CONSTRAINT fk_order_booking_products
        FOREIGN KEY (order_booking_id)
        REFERENCES commerce_warehouse.order_booking (id)
        ON DELETE CASCADE
);

