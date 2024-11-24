INSERT INTO service_category (name, description)
VALUES
('Home Cleaning', 'Comprehensive cleaning services for residential spaces.'),
('Office Cleaning', 'Efficient cleaning solutions for corporate and office spaces.'),
('Bathroom Cleaning', 'Specialized cleaning services for bathrooms and washrooms.'),
('Tapestry Cleaning', 'Professional cleaning services for carpets, upholstery, and drapery.');

INSERT INTO service (name, description, price, service_photo_url)
VALUES
('Deep Cleaning', 'Thorough cleaning of your entire space.', 100.00, '../Image/carouselImage1.jpg'),
('Window Cleaning', 'Sparkling clean windows for your home or office.', 50.00, '../Image/carouselImage1.jpg'),
('Carpet Shampooing', 'Professional shampooing for carpets and rugs.', 75.00, '../Image/carouselImage1.jpg'),
('Office Desk Sanitization', 'Sanitization of office desks and common areas.', 60.00, '../Image/carouselImage1.jpg'),
('Kitchen Deep Clean', 'Detailed cleaning of your kitchen space.', 120.00, '../Image/carouselImage1.jpg'),
('Bathroom Sanitization', 'Complete sanitization of bathroom spaces.', 90.00, '../Image/carouselImage1.jpg'),
('Sofa Cleaning', 'Deep cleaning of sofas and upholstery.', 85.00, '../Image/carouselImage1.jpg'),
('Hardwood Floor Polishing', 'Polishing and maintenance of hardwood floors.', 110.00, '../Image/carouselImage1.jpg'),
('Trash Removal', 'Efficient removal of trash and debris.', 40.00, '../Image/carouselImage1.jpg'),
('Drapery Steaming', 'Steam cleaning for drapery and curtains.', 70.00, '../Image/carouselImage1.jpg');

INSERT INTO service_service_category (service_id, service_category_id)
VALUES
(1, 1), -- Deep Cleaning for Home Cleaning
(1, 2), -- Deep Cleaning for Office Cleaning
(2, 1), -- Window Cleaning for Home Cleaning
(2, 2), -- Window Cleaning for Office Cleaning
(3, 1), -- Carpet Shampooing for Home Cleaning
(3, 4), -- Carpet Shampooing for Tapestry Cleaning
(4, 2), -- Office Desk Sanitization for Office Cleaning
(5, 1), -- Kitchen Deep Clean for Home Cleaning
(6, 3), -- Bathroom Sanitization for Bathroom Cleaning
(7, 4), -- Sofa Cleaning for Tapestry Cleaning
(8, 1), -- Hardwood Floor Polishing for Home Cleaning
(9, 1), -- Trash Removal for Home Cleaning
(9, 2), -- Trash Removal for Office Cleaning
(10, 4); -- Drapery Steaming for Tapestry Cleaning

INSERT INTO Schedule (start_time, end_time)
VALUES
('09:00:00', '11:00:00'),
('11:00:00', '13:00:00'),
('14:00:00', '16:00:00');

INSERT INTO Role (name, description)
VALUES
('admin', 'i am an admin and have a lot of control over many things'),
('client', 'i am a client and am able to book and stuff but nothing dangerous');

INSERT INTO Users (password, email, gender, name, profile_photo_url, role_id)
VALUES ('$2a$10$OvWddNDAplmdFHwBGP8DnuSro35HcJ3seclfhW9b/SQfqhZIegAbK', 'mrTan@gmail.com', 'M', 'Mr Tan', '../Image/defaultpic.png', 1);
--password: 1234 (unhashed version)