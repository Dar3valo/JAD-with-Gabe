-- Order of tables to be created:
-- Roles, Schedule, Service_Categories, Users, Active_Sessions, Bookings, Services, Services_Service_Categories

DROP TABLE IF EXISTS Services_Service_Categories CASCADE;
DROP TABLE IF EXISTS Services CASCADE;
DROP TABLE IF EXISTS Bookings CASCADE;
DROP TABLE IF EXISTS Active_Sessions CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Service_Categories CASCADE;
DROP TABLE IF EXISTS Schedules CASCADE;
DROP TABLE IF EXISTS Roles CASCADE;

-- Roles
CREATE TABLE Roles (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(30),
    description TEXT
);

-- Schedules
CREATE TABLE Schedules (
    schedule_id SERIAL PRIMARY KEY,
    start_time TIME,
    end_time TIME
);

-- Service_Categories
CREATE TABLE Service_Categories (
    service_category_id SERIAL PRIMARY KEY,
    name VARCHAR(40),
    description TEXT
);

-- Users
CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    password VARCHAR(20),
    email VARCHAR(40),
    profile_photo_url VARCHAR(255),
    role_id INT
);

-- Active_Sessions
CREATE TABLE Active_Sessions (
    active_session_hash VARCHAR(255) PRIMARY KEY,
    start TIMESTAMP,
    duration TIMESTAMP,
    user_id INT
);

-- Bookings
CREATE TABLE Bookings (
    booking_id SERIAL PRIMARY KEY,
    schedule_id INT,
    date DATE,
    creation_date TIMESTAMP,
    special_request TEXT,
    user_id INT
);

-- Services
CREATE TABLE Services (
    service_id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(60),
    price DECIMAL(10, 2),
    service_photo_url VARCHAR(255),
    service_category_id INT,
    user_id INT
);

-- Services_Service_Categories
CREATE TABLE Services_Service_Categories (
    service_id INT,
    service_category_id INT
);

-- Adding foreign key constraints
ALTER TABLE Users
ADD CONSTRAINT fk_users_roles FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Active_Sessions
ADD CONSTRAINT fk_active_sessions_users FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Bookings
ADD CONSTRAINT fk_bookings_schedules FOREIGN KEY (schedule_id) REFERENCES Schedules(schedule_id) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT fk_bookings_users FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Services
ADD CONSTRAINT fk_services_service_categories FOREIGN KEY (service_category_id) REFERENCES Service_Categories(service_category_id) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT fk_services_users FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Services_Service_Categories
ADD CONSTRAINT fk_services_service_categories_service FOREIGN KEY (service_id) REFERENCES Services(service_id) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT fk_services_service_categories_category FOREIGN KEY (service_category_id) REFERENCES Service_Categories(service_category_id) ON DELETE CASCADE ON UPDATE CASCADE;
