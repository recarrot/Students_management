package com.example.student.service;

import com.example.student.dao.StudentDao;
import com.example.student.dao.StudentDaoImpl;
import com.example.student.model.Student;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

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

        studentDao.addStudent(student);

        assertNotNull("学生ID应为非空", student.getId());
        Student fetchedStudent = studentDao.getStudentById(student.getId());
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

        studentDao.addStudent(student);

        student.setName("王五");
        student.setAge(23);
        studentDao.updateStudent(student);

        Student updatedStudent = studentDao.getStudentById(student.getId());
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

        studentDao.addStudent(student);

        int studentId = student.getId();
        studentDao.deleteStudent(studentId);

        Student deletedStudent = studentDao.getStudentById(studentId);
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

        studentDao.addStudent(student);

        Student fetchedStudent = studentDao.getStudentById(student.getId());
        assertNotNull("获取的学生不应为空",fetchedStudent );
        assertEquals("学生姓名应匹配","孙七", fetchedStudent.getName() );
    }

    @Test
    public void testGetAllStudents() {
        // 清空数据库中的学生数据（可选）
        List<Student> students = studentDao.getAllStudents();
        for (Student s : students) {
            studentDao.deleteStudent(s.getId());
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

        studentDao.addStudent(student1);
        studentDao.addStudent(student2);

        List<Student> allStudents = studentDao.getAllStudents();
        assertEquals("学生列表应包含 2 名学生",2, allStudents.size() );
    }
}
