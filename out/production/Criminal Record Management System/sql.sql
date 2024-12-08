-- Create Database
CREATE DATABASE crimedb;

-- Use the Database
USE crimedb;

-- 1. Create Table for Users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user') DEFAULT 'user' NOT NULL
);

-- Insert Admin User
INSERT INTO users (username, password, role) VALUES ('admin', 'admin', 'admin');

-- 2. Create Table for Crime Categories
CREATE TABLE crime_categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- Insert Sample Crime Categories
INSERT INTO crime_categories (name, description) VALUES 
('Theft', 'Unlawful taking of property'),
('Assault', 'Physical attack causing harm'),
('Fraud', 'Deceptive acts for personal gain'),
('Cybercrime', 'Criminal activities involving computers'),
('Kidnapping', 'Unlawful abduction of a person');

-- 3. Create Table for Crimes
CREATE TABLE crimes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    date DATE NOT NULL,
    location VARCHAR(255),
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES crime_categories(id) ON DELETE CASCADE
);

-- Insert Sample Crimes
INSERT INTO crimes (title, description, date, location, category_id) VALUES 
('ATM Robbery', 'Unlawful withdrawal of cash from ATM', '2024-02-01', 'MG Road, Bengaluru', 1),
('Domestic Violence', 'Physical abuse reported in family', '2024-03-10', 'DLF City, Gurgaon', 2),
('Credit Card Fraud', 'Unauthorized transactions using stolen card', '2024-04-15', 'Parel, Mumbai', 3),
('Ransomware Attack', 'Data breach demanding ransom', '2024-05-20', 'Sector 5, Noida', 4),
('Child Abduction', 'Reported kidnapping of a minor', '2024-06-01', 'Salt Lake, Kolkata', 5);

-- 4. Create Table for Suspects
CREATE TABLE suspects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    gender ENUM('Male', 'Female', 'Other'),
    address VARCHAR(255),
    contact_info VARCHAR(50)
);

-- Insert Sample Suspects
INSERT INTO suspects (name, age, gender, address, contact_info) VALUES 
('Rajesh Sharma', 34, 'Male', 'Rajaji Nagar, Bengaluru', '9845098450'),
('Sunita Mehta', 28, 'Female', 'MG Road, Pune', '9123456789'),
('Ajay Singh', 40, 'Male', 'Connaught Place, Delhi', '9812345678'),
('Priya Nair', 25, 'Female', 'Marine Drive, Mumbai', '9876543210'),
('Karan Verma', 30, 'Male', 'Park Street, Kolkata', '9654321876');

-- 5. Create Table for Victims
CREATE TABLE victims (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    gender ENUM('Male', 'Female', 'Other'),
    address VARCHAR(255),
    contact_info VARCHAR(50)
);

-- Insert Sample Victims
INSERT INTO victims (name, age, gender, address, contact_info) VALUES 
('Ananya Mishra', 27, 'Female', 'BTM Layout, Bengaluru', '9900990099'),
('Ramesh Gupta', 42, 'Male', 'MG Road, Chennai', '9876123456'),
('Sonal Kapoor', 35, 'Female', 'Powai, Mumbai', '9765432198'),
('Vikram Chatterjee', 29, 'Male', 'Salt Lake, Kolkata', '9832123456'),
('Meera Patel', 23, 'Female', 'Viman Nagar, Pune', '9823456789');

-- 6. Create Table to Link Crimes with Persons (Suspects or Victims)
CREATE TABLE crime_person_links (
    id INT AUTO_INCREMENT PRIMARY KEY,
    crime_id INT NOT NULL,
    person_id INT NOT NULL,
    role ENUM('suspect', 'victim') NOT NULL,
    FOREIGN KEY (crime_id) REFERENCES crimes(id) ON DELETE CASCADE,
    FOREIGN KEY (person_id) REFERENCES suspects(id) ON DELETE CASCADE
);

-- Insert Sample Links Between Crimes and Suspects/Victims
INSERT INTO crime_person_links (crime_id, person_id, role) VALUES 
(1, 1, 'suspect'), -- Rajesh Sharma linked to "ATM Robbery" as a suspect
(1, 1, 'victim'),  -- Rajesh Sharma also reported as a victim
(2, 2, 'suspect'), -- Sunita Mehta linked to "Domestic Violence" as a suspect
(3, 3, 'suspect'), -- Ajay Singh linked to "Credit Card Fraud" as a suspect
(4, 4, 'victim');  -- Priya Nair linked to "Ransomware Attack" as a victim
