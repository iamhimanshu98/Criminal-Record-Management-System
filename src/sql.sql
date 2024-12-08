-- Create the database
CREATE DATABASE crimedb;

-- Use the database
USE crimedb;

-- Create table for Admin login credentials
CREATE TABLE admin_credentials (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   username VARCHAR(50) NOT NULL,
                                   password VARCHAR(100) NOT NULL
);

-- Insert admin credentials (username: admin, password: admin)
INSERT INTO admin_credentials (username, password)
VALUES ('admin', 'admin');

CREATE TABLE crimereports (
                              crime_id INT AUTO_INCREMENT PRIMARY KEY,
                              crime_type VARCHAR(100),
                              crime_date DATE,
                              crime_location VARCHAR(255),
                              victim_id INT,
                              description TEXT,
                              reported_by VARCHAR(100),
                              reporting_date DATE,
                              status VARCHAR(50),
                              FOREIGN KEY (victim_id) REFERENCES victims(victim_id) ON DELETE CASCADE
);

-- Create table for Crime Categories
CREATE TABLE crime_categories (
                                  category_id INT AUTO_INCREMENT PRIMARY KEY,
                                  category_name VARCHAR(100) NOT NULL
);

-- Sample crime category data
INSERT INTO crime_categories (category_name)
VALUES
    ('Theft'),
    ('Assault'),
    ('Fraud'),
    ('Robbery'),
    ('Harassment');


CREATE TABLE victims (
                         victim_id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         age INT NOT NULL,
                         gender ENUM('Male', 'Female', 'Other') NOT NULL,
                         city VARCHAR(100) NOT NULL,
                         contact VARCHAR(15) NOT NULL,
                         crime_type varchar(100) NOT NULL
);

INSERT INTO victims (name, age, gender, city, contact, crime_type) VALUES
                                                                       ('Ravi Sharma', 30, 'Male', 'Delhi', '9876543210', 'Assault'),
                                                                       ('Priya Iyer', 25, 'Female', 'Mumbai', '9988776655', 'Theft'),
                                                                       ('Aarav Kumar', 40, 'Male', 'Bangalore', '9123456789', 'Robbery');

CREATE TABLE suspects (
                          suspect_id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          age INT NOT NULL,
                          gender ENUM('Male', 'Female', 'Other') NOT NULL,
                          city VARCHAR(100) NOT NULL,
                          crime_type VARCHAR(100) NOT NULL
);


INSERT INTO suspects (name, age, gender, city, crime_type)
VALUES
    ('Rahul Sharma', 28, 'Male', 'Mumbai', 'Robbery'),
    ('Priya Patel', 34, 'Female', 'Delhi', 'Fraud'),
    ('Amit Kumar', 40, 'Other', 'Bangalore', 'Assault');


INSERT INTO crimereports (crime_type, crime_date, crime_location, victim_id, description, reported_by, reporting_date, status)
VALUES
('Theft', '2024-01-15', 'Mumbai, Maharashtra', 1, 'Stolen jewelry and cash from the victims house during the night.', 'Anjali Sharma', '2024-01-16', 'Under Investigation'),
('Assault', '2024-02-12', 'Delhi', 'Delhi', 2, 'The victim was physically attacked by an unknown person near the market.', 'Ravi Kumar', '2024-02-13', 'Reported'),
('Fraud', '2024-03-05', 'Bangalore, Karnataka', 3, 'The victim was tricked into transferring money to a fraudulent account through a phishing attack.', 'Priya Reddy', '2024-03-06', 'Closed'),
('Robbery', '2024-04-20', 'Chennai, Tamil Nadu', 4, 'The victim was robbed at gunpoint on the way home from work.', 'Vikas Yadav', '2024-04-21', 'Under Investigation'),
('Harassment', '2024-05-18', 'Hyderabad, Telangana', 5, 'The victim has been receiving threatening calls and messages from the suspect.', 'Meera Patel', '2024-05-19', 'Under Investigation'),
('Murder', '2024-06-25', 'Kolkata, West Bengal', 6, 'The victim was found dead under suspicious circumstances. The cause of death is under investigation.', 'Sourav Banerjee', '2024-06-26', 'Ongoing Investigation'),
('Theft', '2024-07-10', 'Ahmedabad, Gujarat', 7, 'Laptop and mobile phone stolen from the victim apartment.', 'Rohit Desai', '2024-07-11', 'Closed'),
('Rape', '2024-08-02', 'Jaipur, Rajasthan', 8, 'The victim was assaulted by an unknown assailant while walking in the park at night.', 'Neha Gupta', '2024-08-03', 'Under Investigation'),
('Burglary', '2024-09-15', 'Pune, Maharashtra', 9, 'Burglary at a residence, with valuables and cash taken.', 'Karan Singh', '2024-09-16', 'Reported'),
('Drug Trafficking', '2024-10-10', 'Lucknow, Uttar Pradesh', 10, 'The victim was involved in a drug trafficking ring and arrested by local police.', 'Deepika Mehta', '2024-10-11', 'Closed');
