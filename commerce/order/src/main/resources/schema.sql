CREATE SCHEMA IF NOT EXISTS commerce_order;

DROP TABLE IF EXISTS commerce_order.orders, commerce_order.order_products;

CREATE TABLE IF NOT EXISTS commerce_order.address (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house VARCHAR(50) NOT NULL,
    flat VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS commerce_order.orders (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    username VARCHAR(255) NOT NULL,
    state VARCHAR(50) NOT NULL,
    shopping_cart_id VARCHAR(255) NOT NULL,
    payment_id VARCHAR(255),
    delivery_id VARCHAR(255),
    delivery_weight REAL,
    delivery_volume REAL,
    delivery_fragile BOOLEAN,
    delivery_address_id BIGINT REFERENCES commerce_order.address(id) ON DELETE CASCADE,
    delivery_price NUMERIC(19, 2),
    product_price NUMERIC(19, 2),
    total_price NUMERIC(19, 2)
);

CREATE TABLE IF NOT EXISTS commerce_order.order_products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id VARCHAR(255) REFERENCES commerce_order.orders(id) ON DELETE CASCADE,
    product_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL
);





