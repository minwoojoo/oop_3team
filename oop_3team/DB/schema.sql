CREATE DATABASE IF NOT EXISTS schedule_db;

USE schedule_db;

CREATE TABLE IF NOT EXISTS schedule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    memo TEXT
);
