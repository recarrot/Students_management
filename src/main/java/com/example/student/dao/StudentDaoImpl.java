package com.example.student.dao;

import com.example.student.config.DatabaseConfig;
import com.example.student.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentDaoImpl implements StudentDao {
    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);

    @Override
    public void insert(Student student) throws DaoException {
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
            logger.error("Failed to insert student:{}",student,e);
            throw new DaoException("添加学生失败", e);
        } finally {
            if (conn != null) {
                DatabaseConfig.closeConnection(conn);
            }
        }
    }

    @Override
    public void update(Student student) throws DaoException {
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
                    logger.warn("Failed to update");
                    throw new DaoException("修改失败，没有受影响的行");
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to update student", e);
            throw new DaoException("修改学生信息失败", e);
        } finally {
            if (conn != null) {
                DatabaseConfig.closeConnection(conn);
            }
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM students WHERE id = ?";
        Connection conn = null;
        Student student = findById(id);
        String student_name = "";
        if(student != null) {
            student_name = student.getName();
        }
        try {
            conn = DatabaseConfig.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    logger.warn("Failed to delete student:{},{}",id,student_name);
                    throw new DaoException("删除学生失败: ID " + id + " 不存在");
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
    public Student findById(int id) throws DaoException {
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
    public List<Student> findAll() throws DaoException {
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

    @Override
    public List<Student> findByName(String name) throws DaoException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE name = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){
            stmt.setString(1,name);
                 while (rs.next()){
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
            throw new DaoException("查询学生失败",e);
        }
        return students;
    }

    @Override
    public List<Student> findByMajor(String major) throws DaoException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE major = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){
            stmt.setString(1,major);
            while (rs.next()){
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
            throw new DaoException("查询学生失败",e);
        }
        return students;
    }

    @Override
    public List<Student> findByGender(String gender) throws DaoException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE gender = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){
            stmt.setString(1,gender);
            while (rs.next()){
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
            throw new DaoException("查询学生失败",e);
        }
        return students;
    }

    @Override
    public List<Student> findByEnrollmentDate(LocalDate firstDate, LocalDate lastDate) throws DaoException {
        List<Student> students= new ArrayList<>();
        String sql = "SELECT * FROM students WHERE 1=1 ";
        List<Object> params = new ArrayList<>();
        if(firstDate != null){
            sql += "AND enrollment_date >= ?";
            params.add(firstDate);
        }
        if(lastDate != null){
            sql += "AND enrollment_date <= ?";
            params.add(lastDate);
        }
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){
                for(int i = 0;i<params.size();i++){
                    stmt.setObject(i+1,params.get(i));
                    }
            while (rs.next()){
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
            throw new DaoException("查询学生失败",e);
        }
        return students;
    }

    public List<Student> findByFirstDate(LocalDate firstDate) throws DaoException {
        return findByEnrollmentDate(firstDate,null);
    }

    public List<Student> findByLastDate(LocalDate lastDate) throws DaoException{
        return findByEnrollmentDate(null,lastDate);
    }

    public List<Student> findByDateRange(LocalDate firstDate, LocalDate lastDate) throws DaoException {
        return findByEnrollmentDate(firstDate,lastDate);
    }

}