package com.example.student.service;

import com.example.student.dao.StudentDao;
import com.example.student.dao.StudentDaoImpl;
import com.example.student.model.Student;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class StudentDaoTest {
    private final StudentDao studentDao = new StudentDaoImpl();

    @Test
    public void testAddStudent() {
        Student student = new Student("John Doe", "Male", 20, "Computer Science", LocalDate.now());
        studentDao.addStudent(student);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = studentDao.getAllStudents();

        System.out.printf(students.toString());

        assertFalse(students.isEmpty());
    }
}
