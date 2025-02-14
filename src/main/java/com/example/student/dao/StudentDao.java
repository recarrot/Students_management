package com.example.student.dao;

import com.example.student.model.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentDao {
    void insert(Student student) throws DaoException;
    void update(Student student) throws DaoException;
    void delete(int id) throws DaoException;
    Student findById(int id) throws DaoException;
    List<Student> findAll() throws DaoException;

    List<Student> findByName(String name) throws DaoException;

    List<Student> findByMajor(String major) throws DaoException;
    List<Student> findByGender(String gender) throws DaoException;
    List<Student> findByEnrollmentDate(LocalDate firstDate,LocalDate lastDate) throws DaoException;
}