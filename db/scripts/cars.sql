CREATE TABLE if NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL UNIQUE
);

CREATE TABLE if NOT EXISTS brands (
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL
);

CREATE TABLE if NOT EXISTS bodies (
    id SERIAL PRIMARY KEY,
    name varchar(50) NOT NULL
);

CREATE TABLE if NOT EXISTS cars (
    id SERIAL PRIMARY KEY,
    model varchar(100) NOT NULL,
    year int,
    color varchar(50),
    mileage int,
    brand_id int references brands(id),
    body_id int references bodies(id)
);

CREATE TABLE if NOT EXISTS ads (
    id SERIAL PRIMARY KEY,
    description text,
    created timestamp,
    isActive boolean,
    price int,
    car_id int references cars(id),
    user_id int references users(id)
);

insert into bodies (name) values
    ('Седан'), ('Хетчбек'),
    ('Универсал'), ('Внедорожник'),
    ('Купе'), ('Минивэн'),
    ('Пикап'), ('Лимузин'),
    ('Фургон'), ('Кабриолет');

insert into brands (name) values
    ('AUDI'), ('BMW'),
    ('Chevrolet'), ('Citroen'),
    ('Ford'), ('Honda'),
    ('Hyundai'), ('Infinity'),
    ('KIA'), ('Land Rover'),
    ('Lexus'), ('Mazda'),
    ('Mercedes-Benz'), ('MINI'),
    ('Mitsubishi'), ('Nissan'),
    ('Opel'), ('Peugeot'),
    ('Porsche'), ('Renault'),
    ('Skoda'), ('Suzuki'),
    ('Toyota'), ('Volkswagen');

insert into cars (model, year, color, mileage, brand_id, body_id) values
    ('530d', 2013, 'Черный', 78500, 2, 1),
    ('RIO', 2020, 'Белый', 34155, 9, 2),
    ('Range Rover Sport 5.0', 2022, 'Красный', 500, 10, 4);

insert into users (username, password, email) values
    ('Vasya', '12345', 'vasya@gmail.com'),
    ('Ivan', 'ivan22', 'ivan@gmail.com'),
    ('Maria', 'qwerty', 'mariatr@gmail.com');

insert into ads (description, price, created, isActive, car_id, user_id) values
    ('Отличная машина, 2 хозяина, на ходу', 950000, '2022-02-08 10:00:00', true, 1, 1),
    ('Машина битая, заменен капот и передний бампер', 400000, '2022-02-08 10:00:00', true, 2, 2),
    ('Новая машина', 8400000, '2022-02-08 10:00:00', true, 3, 3);