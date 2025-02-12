CREATE DATABASE student_db;
USE student_db;

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),  -- 变更 ENUM 为 VARCHAR，避免扩展性问题
    age INT,  -- 限制年龄范围
    major VARCHAR(50),
    enrollment_date DATE,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,  -- 允许存时间，并设置默认值
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 自动更新时间
    createdBy VARCHAR(50),
    updatedBy VARCHAR(50)
);
