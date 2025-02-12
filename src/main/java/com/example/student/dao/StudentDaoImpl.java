package com.example.student.dao;

import com.example.student.config.DatabaseConfig;
import com.example.student.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (name, gender, age, major, enrollment_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGender());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getMajor());
            stmt.setDate(5, Date.valueOf(student.getEnrollmentDate()));
            stmt.executeUpdate();

            // 获取生成的主键
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    // 返回新的 Student 对象，包含数据库分配的 ID
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add student", e);
        }
    }

    @Override
    public void updateStudent(Student student) {

    }

    @Override
    public void deleteStudent(int id) {

    }

    @Override
    public Student getStudentById(int id) {
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students"; // 确保查询所有字段
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getInt("age"),
                        rs.getString("major"),
                        rs.getDate("enrollment_date").toLocalDate(),
                        rs.getTimestamp("createdAt") != null ? rs.getTimestamp("createdAt").toLocalDateTime() : null,
                        rs.getTimestamp("updatedAt") != null ? rs.getTimestamp("updatedAt").toLocalDateTime() : null,
                        rs.getString("createdBy"),
                        rs.getString("updatedBy")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch students", e);
        }
        return students;
    }


    // 实现其他方法（updateStudent, deleteStudent, getStudentById）
}