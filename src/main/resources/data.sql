insert into role (id, name)
values (1, 'user');
insert into role (id, name)
values (2, 'admin');
insert into users (id, username, email, password, enabled, role_id)
values (1, 'Anton', 'abc@def',
        '$2a$12$Wdbe4OEBhR/c2Hgbte184uarjDdY56Kb0Hif1d21vzA36XYkGo3rG', true, 1);
insert into users (id, username, email, password, enabled, role_id)
values (2, 'Igor', '123@456',
        '$2a$12$Wdbe4OEBhR/c2Hgbte184uarjDdY56Kb0Hif1d21vzA36XYkGo3rG', true, 2);
insert into cart(id, user_id)
values (1, 1);
insert into cart(id, user_id)
values (2, 2);

insert into type (id, name)
values (1, 'Not selected');
insert into type (id, name)
values (2, 'Car');
insert into type (id, name)
values (3, 'Smartphone');
insert into type (id, name)
values (4, 'Plane');


insert into brand (id, name)
values (1, 'Not selected');
insert into brand (id, name)
values (2, 'Mercedes');
insert into brand (id, name)
values (3, 'BMW');
insert into brand (id, name)
values (4, 'Audi');
insert into brand (id, name)
values (5, 'Apple');
insert into brand (id, name)
values (6, 'Samsung');
insert into brand (id, name)
values (7, 'Boeing');
insert into brand (id, name)
values (8, 'Concorde');
insert into type_brand(type_id, brand_id)
values (2, 2);
insert into type_brand(type_id, brand_id)
values ( 2, 3);
insert into type_brand( type_id, brand_id)
values ( 2, 4);
insert into type_brand(type_id, brand_id)
values (3, 5);
insert into type_brand(type_id, brand_id)
values (3, 6);
insert into type_brand(type_id, brand_id)
values (4, 7);
insert into type_brand(type_id, brand_id)
values (4, 8);
insert into product (id, name, price, type_id, brand_id)
values (1, 'Mercedes S600', 200000, 2, 2);
insert into product (id, name, price, type_id, brand_id)
values (2, 'Mercedes S500', 150000, 2, 2);
insert into product (id, name, price, type_id, brand_id)
values (3, 'BMW 750i', 150000, 2, 3);
insert into product (id, name, price, type_id, brand_id)
values (4, 'Audi A8', 150000, 2, 4);
insert into product (id, name, price, type_id, brand_id)
values (5, 'Audi R8', 250000, 2, 4);
insert into product (id, name, price, type_id, brand_id)
values (6, 'iPhone 15', 1000, 3, 5);
insert into product (id, name, price, type_id, brand_id)
values (7, 'Samsung Galaxy A32', 500, 3, 6);
insert into product (id, name, price, type_id, brand_id)
values (8, 'Samsung Galaxy A36', 1000, 3, 6);
insert into product (id, name, price, type_id, brand_id)
values (9, 'Boeing 737-800', 1000000, 4, 7);
insert into product (id, name, price, type_id, brand_id)
values (10, 'Concorde', 2000000, 4, 8);

