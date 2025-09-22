-- Event Planner Database Schema (PostgreSQL)

-- 1. NotificationMethod Table
CREATE TABLE NotificationMethod (
    method_id SERIAL PRIMARY KEY,
    method_name VARCHAR(20) NOT NULL UNIQUE
);

-- 2. NotificationInterval Table
CREATE TABLE NotificationInterval (
    interval_id SERIAL PRIMARY KEY,
    interval_desc VARCHAR(100) NOT NULL,
    interval_minutes INT NOT NULL
);

-- 3. UserDetails Table
CREATE TABLE UserDetails (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    mobile_number VARCHAR(20),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    security_question VARCHAR(255),
    answer VARCHAR(255),
    method_id INT NOT NULL,
    CONSTRAINT fk_user_method
        FOREIGN KEY (method_id)
        REFERENCES NotificationMethod (method_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- 4. EventDetails Table
CREATE TABLE EventDetails (
    event_id SERIAL PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    event_description TEXT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    interval_id INT NOT NULL,
    CONSTRAINT fk_event_interval
        FOREIGN KEY (interval_id)
        REFERENCES NotificationInterval (interval_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT chk_event_time CHECK (end_time > start_time)
);

-- 5. UserEvents Table (junction for many-to-many relationship)
CREATE TABLE UserEvents (
    user_event_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    event_id INT NOT NULL,
    CONSTRAINT fk_userevent_user
        FOREIGN KEY (user_id)
        REFERENCES UserDetails (user_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_userevent_event
        FOREIGN KEY (event_id)
        REFERENCES EventDetails (event_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT uq_user_event UNIQUE (user_id, event_id)
);
