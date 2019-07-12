INSERT INTO client (id, address, birth_date, first_name, last_name) VALUES (1, "TestAdress 1", "1993-09-08", "Demo", "User");
INSERT INTO client (id, address, birth_date, first_name, last_name) VALUES (2, "TestAdress 99", "1985-11-25", "Hello", "World");

INSERT INTO account (id, balance, number, type, client_id) VALUES (1, 1000, "BANK32798884", 0, 1);
INSERT INTO account (id, balance, number, type, client_id) VALUES (2, 2500, "BANK56508564", 1, 1);
INSERT INTO account (id, balance, number, type, client_id) VALUES (3, 1000, "BANK90137209", 0, 2);
INSERT INTO account (id, balance, number, type, client_id) VALUES (4, 2500, "BANK75637225", 1, 2);

INSERT INTO transaction (id, amount, date, description, type, account_id, contra_account_id) VALUES (1, 100.0, "2019-07-12", "Demo transaction 1", 0, 1, 3);
INSERT INTO transaction (id, amount, date, description, type, account_id, contra_account_id) VALUES (2, 100.0, "2019-07-12", "Demo transaction 2", 1, 1, 3);
INSERT INTO transaction (id, amount, date, description, type, account_id, contra_account_id) VALUES (3, 100.0, "2019-07-12", "Demo transaction 3", 0, 3, 1);
INSERT INTO transaction (id, amount, date, description, type, account_id, contra_account_id) VALUES (4, 100.0, "2019-07-12", "Demo transaction 4", 1, 3, 1);
