CREATE DATABASE student_db;
USE student_db;

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender ENUM('Male', 'Female'),
    age INT,
    major VARCHAR(50),
    enrollment_date DATE
);