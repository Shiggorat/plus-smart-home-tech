CREATE SCHEMA IF NOT EXISTS commerce_delivery;

DROP TABLE IF EXISTS commerce_delivery.delivery, commerce_delivery.address;

CREATE TABLE IF NOT EXISTS commerce_delivery.address (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house VARCHAR(50) NOT NULL,
    flat VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS commerce_delivery.delivery (
    delivery_id VARCHAR(255) PRIMARY KEY NOT NULL,
    delivery_volume FLOAT NOT NULL,
    delivery_weight FLOAT NOT NULL,
    fragile BOOLEAN NOT NULL,
    address_from_id BIGINT REFERENCES commerce_delivery.address(id) ON DELETE CASCADE,
    address_to_id BIGINT REFERENCES commerce_delivery.address(id) ON DELETE CASCADE,
    order_id VARCHAR(255) NOT NULL,
    delivery_state VARCHAR(50)
);
