INSERT INTO account(id, account_id, balance)
VALUES
       (1, 1, 3000),
       (2, 2, 3000),
       (3, 3, 3000);

INSERT INTO transaction(id, transaction_uuid, value, source_account_id, dest_account_id)
VALUES
       (1, 'uuid_1', 100, 1, 2);