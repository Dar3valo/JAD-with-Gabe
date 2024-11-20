-- Drop tables without dependencies first
DROP TABLE IF EXISTS Service_Service_Category;
DROP TABLE IF EXISTS Service_Offered;
DROP TABLE IF EXISTS Cart_Item;

-- Drop tables with dependencies next
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Active_Session;

-- Drop dependent tables' parents
DROP TABLE IF EXISTS Service;
DROP TABLE IF EXISTS Service_Category;

-- Drop users and related tables
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Role;

-- Finally, drop remaining independent tables
DROP TABLE IF EXISTS Schedule;

-- CREATE TABLES
CREATE TABLE Role (
    role_id INT PRIMARY KEY,
    name VARCHAR NOT NULL,
    description TEXT
);

CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    password VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    profile_photo_url VARCHAR,
    role_id INT
);

CREATE TABLE Active_Session (
    active_session_hash VARCHAR PRIMARY KEY,
    start TIMESTAMP NOT NULL,
    duration TIME NOT NULL,
    user_id INT
);

CREATE TABLE Service_Category (
    service_category_id INT PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR
);

CREATE TABLE Service (
    service_id INT PRIMARY KEY,
    name VARCHAR NOT NULL,
    description TEXT,
    price DECIMAL NOT NULL,
    service_photo_url VARCHAR,
    service_category INT
);

CREATE TABLE Service_Service_Category (
    service_id INT,
    service_category_id INT
);

CREATE TABLE Service_Offered (
    service_id INT,
    user_id INT
);

CREATE TABLE Schedule (
    schedule_id INT PRIMARY KEY,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

CREATE TABLE Booking (
    booking_id INT PRIMARY KEY,
    date DATE NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    special_request TEXT,
    user_id INT,
    schedule_id INT
);

CREATE TABLE Cart_Item (
    cart_item_id INT PRIMARY KEY,
    user_id INT
);

-- CREATE FOREIGN KEYS
ALTER TABLE Users
ADD CONSTRAINT fk_user_role
FOREIGN KEY (role_id) REFERENCES Role(role_id);

ALTER TABLE Active_Session
ADD CONSTRAINT fk_active_session_user
FOREIGN KEY (user_id) REFERENCES Users(user_id);

ALTER TABLE Service
ADD CONSTRAINT fk_service_category
FOREIGN KEY (service_category) REFERENCES Service_Category(service_category_id);

ALTER TABLE Service_Service_Category
ADD CONSTRAINT fk_service_service_category_service
FOREIGN KEY (service_id) REFERENCES Service(service_id),
ADD CONSTRAINT fk_service_service_category_category
FOREIGN KEY (service_category_id) REFERENCES Service_Category(service_category_id);

ALTER TABLE Service_Offered
ADD CONSTRAINT fk_service_offered_service
FOREIGN KEY (service_id) REFERENCES Service(service_id),
ADD CONSTRAINT fk_service_offered_user
FOREIGN KEY (user_id) REFERENCES Users(user_id);

ALTER TABLE Booking
ADD CONSTRAINT fk_booking_user
FOREIGN KEY (user_id) REFERENCES Users(user_id),
ADD CONSTRAINT fk_booking_schedule
FOREIGN KEY (schedule_id) REFERENCES Schedule(schedule_id);

ALTER TABLE Cart_Item
ADD CONSTRAINT fk_cart_item_user
FOREIGN KEY (user_id) REFERENCES Users(user_id);
