insert into users (id, username, email, password, enabled) values (1, 'admin', 'abc@def', '$2a$12$Wdbe4OEBhR/c2Hgbte184uarjDdY56Kb0Hif1d21vzA36XYkGo3rG', true);
insert into tokens (id, token, expiry_date, user_id) values ( 1,  null, null, 1);
insert into type (id, name) values (1, 'Car');
insert into type (id, name) values (2, 'Smartphone');
insert into type (id, name) values (3, 'Plane');
insert into type (id, name) values (4, 'Other');

insert into brand (id, name) values (1, 'Mercedes');
insert into brand (id, name) values (2, 'BMW');
insert into brand (id, name) values (3, 'Apple');
insert into brand (id, name) values (4, 'Samsung');
insert into brand (id, name) values (5, 'Audi');
insert into brand (id, name) values (6, 'Volvo');
insert into brand (id, name) values ( 7, 'Boeing' );
insert into brand (id, name) values ( 8, 'Concorde' );
insert into brand (id, name) values ( 9, 'Other' );

insert into type_brand (type_id, brand_id) values (1, 1);
insert into type_brand (type_id, brand_id) values (1, 2);
insert into type_brand (type_id, brand_id) values (2, 3);
insert into type_brand (type_id, brand_id) values (2, 4);
insert into type_brand (type_id, brand_id) values (3, 7);
insert into type_brand (type_id, brand_id) values (3, 8);

insert into product (id, name, price,  type_id, brand_id) values (1, 'S600', 200000,  1, 1);
insert into product (id, name, price,  type_id, brand_id) values (2, '750i', 150000,   1, 2);
insert into product (id, name, price,  type_id, brand_id) values (3, 'iPhone 15', 1000,  2, 3);
insert into product (id, name, price,  type_id, brand_id) values (4, 'Galaxy A32', 500,  2, 4);
insert into product (id, name, price,  type_id, brand_id) values (5, 'Boeing 737-800',  1000000, 3, 7);
insert into product (id, name, price,  type_id, brand_id) values (6, 'Concorde', 2000000,  3, 8);
insert into photo (id,  url, product_id) values ( 1, '', 1);
insert into photo (id,  url, product_id) values ( 2, '', 2);
insert into photo (id, url, product_id) values ( 3, '', 3);
insert into photo (id, url, product_id) values ( 4, '', 4);
insert into photo (id,  url, product_id) values ( 5, '', 5);
insert into photo (id,  url, product_id) values ( 6, '', 6);