DROP TABLE IF EXISTS Service_Service_Category;
DROP TABLE IF EXISTS Service_Offered;
DROP TABLE IF EXISTS Cart_Item;

DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Active_Session;

DROP TABLE IF EXISTS Service;
DROP TABLE IF EXISTS Service_Category;
DROP TABLE IF EXISTS Feedback;

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Role;

DROP TABLE IF EXISTS Schedule;

-- CREATE TABLES
CREATE TABLE Role (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description TEXT
);

CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    password VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
	name VARCHAR NOT NULL,
	gender VARCHAR NOT NULL,
    profile_photo_url VARCHAR,
    role_id INT DEFAULT 2
);

CREATE TABLE Feedback (
    feedback_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    sources VARCHAR(255), -- Social Media, Friends, etc.
    other_sources TEXT,
    comments TEXT,
    improvements TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Active_Session (
    active_session_unhash VARCHAR PRIMARY KEY,
    start TIMESTAMP NOT NULL,
    duration TIME NOT NULL,
    user_id INT
);

CREATE TABLE Service_Category (
    service_category_id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR
);

CREATE TABLE Service (
    service_id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description TEXT,
    price DECIMAL NOT NULL,
    service_photo_url VARCHAR
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
    schedule_id SERIAL PRIMARY KEY,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

CREATE TABLE Booking (
    booking_id SERIAL PRIMARY KEY,
    booking_date DATE NOT NULL,
	special_request TEXT NOT NULL,
	main_address VARCHAR NOT NULL,
	postal_code VARCHAR	NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_id INT,
    schedule_id INT,
	service_id INT
);

CREATE TABLE Cart_Item (
    cart_item_id SERIAL PRIMARY KEY,
	booking_date DATE NOT NULL,
	special_request TEXT NOT NULL,
	main_address VARCHAR NOT NULL,
	postal_code INT	NOT NULL,
    user_id INT,
    schedule_id INT,
	service_id INT
);

-- define foreign key
ALTER TABLE Users
ADD CONSTRAINT fk_user_role
FOREIGN KEY (role_id) REFERENCES Role(role_id);

ALTER TABLE Active_Session
ADD CONSTRAINT fk_active_session_user
FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE;

ALTER TABLE Service_Service_Category
ADD CONSTRAINT fk_Service_Service_Category_Service
FOREIGN KEY (service_id) REFERENCES Service(service_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_Service_Service_Category_Category
FOREIGN KEY (service_category_id) REFERENCES Service_category(service_category_id) ON DELETE CASCADE;

ALTER TABLE Service_Offered
ADD CONSTRAINT fk_service_offered_service
FOREIGN KEY (service_id) REFERENCES Service(service_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_service_offered_user
FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE;

ALTER TABLE Booking
ADD CONSTRAINT fk_booking_user
FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_booking_schedule
FOREIGN KEY (schedule_id) REFERENCES Schedule(schedule_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_booking_service
FOREIGN KEY (service_id) REFERENCES Service(service_id) ON DELETE CASCADE;

ALTER TABLE Cart_Item
ADD CONSTRAINT fk_cart_item_user
FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_cart_item_schedule
FOREIGN KEY (schedule_id) REFERENCES Schedule(schedule_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_cart_item_service
FOREIGN KEY (service_id) REFERENCES Service(service_id) ON DELETE CASCADE;

ALTER TABLE Feedback
ADD CONSTRAINT fk_feedback_user
FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE;

-- triggers
CREATE OR REPLACE FUNCTION delete_orphaned_services()
RETURNS TRIGGER AS $$
BEGIN
    -- Delete the service if it has no more associations in service_service_category
    DELETE FROM service
    WHERE service_id = OLD.service_id
    AND NOT EXISTS (
        SELECT 1
        FROM service_service_category
        WHERE service_id = OLD.service_id
    );

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_delete_orphaned_services
AFTER DELETE ON service_service_category
FOR EACH ROW
EXECUTE FUNCTION delete_orphaned_services();