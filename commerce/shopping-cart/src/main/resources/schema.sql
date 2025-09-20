CREATE SCHEMA IF NOT EXISTS commerce_cart;

DROP TABLE IF EXISTS commerce_cart.cart_products, commerce_cart.cart;

CREATE TABLE IF NOT EXISTS commerce_cart.cart (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    cart_state VARCHAR(50) NOT NULL,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS commerce_cart.cart_products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cart_id VARCHAR(255) REFERENCES commerce_cart.cart(id) ON DELETE CASCADE,
    product_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL
)
