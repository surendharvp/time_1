-- Insert sample skills
INSERT INTO skills (name, category, description) VALUES
('Web Development', 'Technology', 'Full-stack web development services'),
('Graphic Design', 'Design', 'Visual design and branding services'),
('Language Teaching', 'Education', 'Language tutoring and instruction'),
('Home Repair', 'Maintenance', 'General home maintenance and repair'),
('Photography', 'Creative', 'Professional photography services');

-- Insert sample users (password is 'password' hashed with BCrypt)
INSERT INTO users (email, password, name, time_balance) VALUES
('john@example.com', '$2a$10$rS.rJMR6YZvwxHG6hHzW8.KNFv4QK.WJ8l8K6z5pfQW.TWzUVx9hy', 'John Doe', 10),
('jane@example.com', '$2a$10$rS.rJMR6YZvwxHG6hHzW8.KNFv4QK.WJ8l8K6z5pfQW.TWzUVx9hy', 'Jane Smith', 15),
('bob@example.com', '$2a$10$rS.rJMR6YZvwxHG6hHzW8.KNFv4QK.WJ8l8K6z5pfQW.TWzUVx9hy', 'Bob Wilson', 5);

-- Link users with skills
INSERT INTO user_skills (user_id, skill_id, proficiency_level, verified) VALUES
(1, 1, 'Expert', true),
(1, 2, 'Intermediate', true),
(2, 3, 'Expert', true),
(3, 4, 'Expert', true);

-- Insert sample requests
INSERT INTO requests (user_id, title, description, estimated_hours, status, category, skill_id) VALUES
(1, 'Need Website Development', 'Looking for someone to build a small business website', 20, 'open', 'Technology', 1),
(2, 'Spanish Tutoring Required', 'Need help improving my Spanish speaking skills', 10, 'open', 'Education', 3),
(3, 'Home Repairs Needed', 'Multiple small repairs needed around the house', 5, 'open', 'Maintenance', 4);