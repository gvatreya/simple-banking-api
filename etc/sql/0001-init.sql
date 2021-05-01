-- Initial database setup

CREATE SCHEMA IF NOT EXISTS finmid;

CREATE TABLE IF NOT EXISTS finmid_accounts(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT,
    balance DOUBLE,
    created DATETIME,
    updated DATETIME,
    deleted DATETIME
);

CREATE TABLE IF NOT EXISTS finmid_transactions(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_uuid VARCHAR(16),
    value DOUBLE,
    source_account_id BIGINT,
    dest_account_id BIGINT,
    time DATETIME
);