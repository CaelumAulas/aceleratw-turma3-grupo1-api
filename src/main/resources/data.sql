INSERT INTO USERS(username, password) VALUES('teste', '$2a$10$Ijf4rM7Mor2U5zN9Zjz5qOofRq0Qy.MtWdRqtUp99TXTFYEnytOpO');
INSERT INTO USERS(username, password) VALUES('teste2', '$2a$10$Ijf4rM7Mor2U5zN9Zjz5qOofRq0Qy.MtWdRqtUp99TXTFYEnytOpO');

INSERT INTO PROFILE(name) VALUES ('admin');

INSERT INTO USERS_PROFILES(users_id, profiles_id) VALUES (1,1);
INSERT INTO USERS_PROFILES(users_id, profiles_id) VALUES (2,1);

INSERT INTO BRAND(name) VALUES ('Ford');
INSERT INTO BRAND(name) VALUES ('Fiat');

INSERT INTO VEHICLE(model, year, price, brand_id) VALUES ('Ka', 2021, 24000.99, 1);
INSERT INTO VEHICLE(model, year, price, brand_id) VALUES ('Focus', 2021, 30000.99, 1);
INSERT INTO VEHICLE(model, year, price, brand_id) VALUES ('Fusion', 2020, 40000.99, 1);

INSERT INTO VEHICLE(model, year, price, brand_id) VALUES ('Uno', 2018, 19000.99, 2);