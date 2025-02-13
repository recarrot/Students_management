package com.example.student.dao;

import com.example.student.config.DatabaseConfig;
import com.example.student.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public void addStudent(Student student) {
        Connection conn = null;
        String sql = "INSERT INTO students (name, gender, age, major, enrollment_date) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = DatabaseConfig.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, student.getName());
                stmt.setString(2, student.getGender());
                stmt.setInt(3, student.getAge());
                stmt.setString(4, student.getMajor());
                stmt.setDate(5, Date.valueOf(student.getEnrollmentDate()));
                stmt.executeUpdate();

                // 如果需要，可以获取生成的主键并设置到 Student 对象中
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        student.setId(generatedId); // 设置生成的主键
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add student", e);
        } finally {
            if (conn != null) {
                DatabaseConfig.closeConnection(conn);
            }
        }
    }

    @Override
    public void updateStudent(Student student) {
        Connection conn = null;
        String sql = "UPDATE students SET name=?, gender=?, age=?, major=?, enrollment_date=? WHERE id=?";
        try {
            conn = DatabaseConfig.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, student.getName());
                stmt.setString(2, student.getGender());
                stmt.setInt(3, student.getAge());
                stmt.setString(4, student.getMajor());
                stmt.setDate(5, Date.valueOf(student.getEnrollmentDate()));
                stmt.setInt(6,student.getId());
                int affectedRows = stmt.executeUpdate();

                if(affectedRows == 0){
                    throw new SQLException("修改失败，没有受影响的行");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update student", e);
        } finally {
            if (conn != null) {
                DatabaseConfig.closeConnection(conn);
            }
        }
    }

    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("删除学生失败: ID " + id + " 不存在");
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException("删除学生失败, ID: " + id, e);
        }
        finally {
            DatabaseConfig.closeConnection(conn);
        }
    }

    @Override
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Student(
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
            }
        }catch (SQLException e) {
            throw new RuntimeException("Failed to fetch students", e);
        }
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


}