CREATE SCHEMA IF NOT EXISTS commerce_payment;

DROP TABLE IF EXISTS commerce_payment.payment;

CREATE TABLE commerce_payment.payment (
    payment_id     VARCHAR(255) PRIMARY KEY,
    product_price  NUMERIC(19, 2) NOT NULL,
    delivery_price NUMERIC(19, 2) NOT NULL,
    total_price    NUMERIC(19, 2) NOT NULL,
    payment_state  VARCHAR(50) NOT NULL,
    order_id       VARCHAR(255) NOT NULL
);