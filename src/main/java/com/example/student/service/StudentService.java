package com.example.student.service;

import com.example.student.dto.StudentQuery;
import com.example.student.model.Student;

import java.util.List;

public interface StudentService {
    // 添加学生
    Student addStudent(Student student) throws ServiceException;

    // 根据ID删除学生
    void deleteStudent(Long id) throws ServiceException;

    // 更新学生信息
    Student updateStudent(Student student) throws ServiceException;

    // 根据ID查询学生
    Student getStudentById(Long id) throws ServiceException;

    // 获取所有学生
    List<Student> getAllStudents() throws ServiceException;

    // 根据姓名模糊查询
    List<Student> findStudentsByName(String name) throws ServiceException;

    // 分页查询学生列表
    List<Student> getStudentsByPage(int page, int size) throws ServiceException;

    // 获取学生总数
    long getStudentCount() throws ServiceException;

    // 检查学号是否存在
    boolean isStudentNoExists(String studentNo) throws ServiceException;

    // 批量导入学生
    List<Student> batchImportStudents(List<Student> students) throws ServiceException;

    // 批量删除学生
    void batchDeleteStudents(List<Long> ids) throws ServiceException;

    // 验证学生信息
    void validateStudent(Student student) throws ServiceException;

    // 根据条件查询学生
    List<Student> findStudentsByCondition(StudentQuery query) throws ServiceException;
}
