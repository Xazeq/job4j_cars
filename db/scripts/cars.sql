CREATE TABLE if NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    phone varchar(255) NOT NULL UNIQUE
);

CREATE TABLE if NOT EXISTS brands (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE if NOT EXISTS bodies (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE if NOT EXISTS ads (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    description text,
    created timestamp,
    isActive boolean
    brand_id int references brand(id),
    body_id references bodies(id),
    user_id references user(id)
);