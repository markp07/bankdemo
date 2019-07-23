INSERT INTO client (id, address, birth_date, first_name, last_name) VALUES (1, "Test Street 1", "1993-09-08", "Foo", "Bar");
INSERT INTO client (id, address, birth_date, first_name, last_name) VALUES (2, "Demo Road 1", "1975-12-25", "Hello", "World");

INSERT INTO account (id, balance, number, type, client_id) VALUES (1, 1000, "BANK32798884", 0, 1);
INSERT INTO account (id, balance, number, type, client_id) VALUES (2, 2500, "BANK56508564", 1, 1);
INSERT INTO account (id, balance, number, type, client_id) VALUES (3, 1000, "BANK90137209", 0, 2);
INSERT INTO account (id, balance, number, type, client_id) VALUES (4, 2500, "BANK75637225", 1, 2);

INSERT INTO transaction (id, amount, date, description, type, account_id, contra_account_id) VALUES (1, 100.0, "2019-07-01", "Demo Transaction #1", 0, 1, 3);
INSERT INTO transaction (id, amount, date, description, type, account_id, contra_account_id) VALUES (2, 125.5, "2019-07-04", "Demo Transaction #2", 1, 1, 3);
INSERT INTO transaction (id, amount, date, description, type, account_id, contra_account_id) VALUES (3, 100.0, "2019-07-07", "Demo Transaction #3", 0, 3, 1);
INSERT INTO transaction (id, amount, date, description, type, account_id, contra_account_id) VALUES (4, 125.5, "2019-07-10", "Demo Transaction #4", 1, 3, 1);