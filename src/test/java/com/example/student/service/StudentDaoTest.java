package com.example.student.service;

import com.example.student.dao.DaoException;
import com.example.student.dao.StudentDaoImpl;
import com.example.student.model.Student;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class StudentDaoTest {

    private final StudentDaoImpl studentDao = new StudentDaoImpl();


    @Test
    public void testAddStudent() {
        Student student = new Student();
        student.setName("张三");
        student.setGender("男");
        student.setAge(20);
        student.setMajor("计算机科学");
        student.setEnrollmentDate(LocalDate.now());

        try {
            studentDao.insert(student);
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }

        Student fetchedStudent;
        try {
            fetchedStudent = studentDao.findById(student.getId());
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }
        assertNotNull( "获取的学生不应为空",fetchedStudent);
        String name =fetchedStudent.getName();
        assertEquals("学生姓名应匹配", "张三", name);
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student();
        student.setName("李四");
        student.setGender("女");
        student.setAge(22);
        student.setMajor("软件工程");
        student.setEnrollmentDate(LocalDate.now());

        try {
            studentDao.insert(student);
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }

        student.setName("王五");
        student.setAge(23);
        try {
            studentDao.update(student);
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }

        Student updatedStudent;
        try {
            updatedStudent = studentDao.findById(student.getId());
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }
        assertNotNull("更新后的学生不应为空",updatedStudent);
        assertEquals("学生姓名应更新为 '王五'","王五", updatedStudent.getName() );
        assertEquals("学生年龄应更新为 23", 23, updatedStudent.getAge());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        student.setName("赵六");
        student.setGender("男");
        student.setAge(21);
        student.setMajor("信息安全");
        student.setEnrollmentDate(LocalDate.now());

        try {
            studentDao.insert(student);
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }

        int studentId = student.getId();
        try {
            studentDao.delete(studentId);
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }

        Student deletedStudent;
        try {
            deletedStudent = studentDao.findById(studentId);
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }
        assertNull("删除后的学生应为空", deletedStudent );
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student();
        student.setName("孙七");
        student.setGender("女");
        student.setAge(19);
        student.setMajor("人工智能");
        student.setEnrollmentDate(LocalDate.now());

        try {
            studentDao.insert(student);
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }

        Student fetchedStudent;
        try {
            fetchedStudent = studentDao.findById(student.getId());
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }
        assertNotNull("获取的学生不应为空",fetchedStudent );
        assertEquals("学生姓名应匹配","孙七", fetchedStudent.getName() );
    }

    @Test
    public void testGetAllStudents() {
        // 清空数据库中的学生数据（可选）
        List<Student> students;
        try {
            students = studentDao.findAll();
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }
        for (Student s : students) {
            try {
                studentDao.delete(s.getId());
            } catch (com.example.student.dao.DaoException e) {
                throw new RuntimeException(e);
            }
        }

        Student student1 = new Student();
        student1.setName("周八");
        student1.setGender("男");
        student1.setAge(20);
        student1.setMajor("数据科学");
        student1.setEnrollmentDate(LocalDate.now());

        Student student2 = new Student();
        student2.setName("吴九");
        student2.setGender("女");
        student2.setAge(21);
        student2.setMajor("网络安全");
        student2.setEnrollmentDate(LocalDate.now());

        try {
            studentDao.insert(student1);
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }
        try {
            studentDao.insert(student2);
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }

        List<Student> allStudents;
        try {
            allStudents = studentDao.findAll();
        } catch (com.example.student.dao.DaoException e) {
            throw new RuntimeException(e);
        }
        assertEquals("学生列表应包含 2 名学生",2, allStudents.size() );
    }

    @Test
    public void testGetStudentByName() throws DaoException{
        studentDao.findByName("张三");
    }

    @Test
    public void testGetStudentByMajor() throws DaoException{
        studentDao.findByMajor("三年级");
    }

    @Test
    public void testGetStudentByGender() throws DaoException{
        studentDao.findByGender("男");
    }

    @Test
    public void testGetStudentsByFirstDate() throws DaoException {
        studentDao.findByFirstDate(LocalDate.of(2024,1,1));
    }

    @Test
    public void testGetStudentByLastDate() throws DaoException{
        studentDao.findByLastDate(LocalDate.of(2025,12,12));
    }

    @Test
    public void testGetStudentByDateRange() throws DaoException{
        studentDao.findByDateRange(
                LocalDate.of(2024,1,1),
                LocalDate.of(2025,12,12));
    }
}
