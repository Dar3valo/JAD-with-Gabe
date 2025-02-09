-- Drop tables in reverse order to handle dependencies
DROP TABLE IF EXISTS Feedback;
DROP TABLE IF EXISTS Cart_Item;
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Service_Offered;
DROP TABLE IF EXISTS Service_Service_Category;
DROP TABLE IF EXISTS Active_Session;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Service;
DROP TABLE IF EXISTS Service_Category;
DROP TABLE IF EXISTS Schedule;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS Status;

-- Create Status table (no dependencies)
CREATE TABLE Status (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create Role table (no dependencies)
CREATE TABLE Role (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create Schedule table (no dependencies)
CREATE TABLE Schedule (
    schedule_id INT PRIMARY KEY AUTO_INCREMENT,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

-- Create Service_Category table (no dependencies)
CREATE TABLE Service_Category (
    service_category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

-- Create Service table (no dependencies)
CREATE TABLE Service (
    service_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    service_photo_url VARCHAR(255)
);

-- Create Users table (depends on Role and Status)
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    gender CHAR(1),
    name VARCHAR(20) NOT NULL,
    profile_photo_url VARCHAR(255),
    total_spent DECIMAL(10,2) DEFAULT 0.00,
    role_id INT,
    reset_token VARCHAR(255),
    reset_expiry TIMESTAMP,
    status_id INT,
    FOREIGN KEY (role_id) REFERENCES Role(role_id),
    FOREIGN KEY (status_id) REFERENCES Status(status_id)
);

-- Create Active_Session table (depends on Users)
CREATE TABLE Active_Session (
    active_session_unhash VARCHAR(255) PRIMARY KEY,
    start TIMESTAMP NOT NULL,
    duration TIME NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Create Service_Service_Category table (depends on Service and Service_Category)
CREATE TABLE Service_Service_Category (
    service_id INT,
    service_category_id INT,
    PRIMARY KEY (service_id, service_category_id),
    FOREIGN KEY (service_id) REFERENCES Service(service_id),
    FOREIGN KEY (service_category_id) REFERENCES Service_Category(service_category_id)
);

-- Create Service_Offered table (depends on Service and Users)
CREATE TABLE Service_Offered (
    service_id INT,
    user_id INT,
    PRIMARY KEY (service_id, user_id),
    FOREIGN KEY (service_id) REFERENCES Service(service_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Create Booking table (depends on Users, Schedule, Status, and Service)
CREATE TABLE Booking (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_date DATE NOT NULL,
    special_request TEXT,
    main_address VARCHAR(255) NOT NULL,
    postal_code INT NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT,
    schedule_id INT,
    status_id INT,
    service_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (schedule_id) REFERENCES Schedule(schedule_id),
    FOREIGN KEY (status_id) REFERENCES Status(status_id),
    FOREIGN KEY (service_id) REFERENCES Service(service_id)
);

-- Create Cart_Item table (depends on Users, Schedule, and Service)
CREATE TABLE Cart_Item (
    cart_item_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_date DATE NOT NULL,
    special_request TEXT,
    main_address VARCHAR(255) NOT NULL,
    postal_code INT NOT NULL,
    user_id INT,
    schedule_id INT,
    service_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (schedule_id) REFERENCES Schedule(schedule_id),
    FOREIGN KEY (service_id) REFERENCES Service(service_id)
);

-- Create Feedback table (depends on Users)
CREATE TABLE Feedback (
    feedback_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    rating INT NOT NULL,
    sources VARCHAR(255),
    other_sources TEXT,
    comments TEXT,
    improvements TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);