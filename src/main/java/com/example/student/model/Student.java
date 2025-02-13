package com.example.student.model;

import lombok.Data;
import lombok.Builder;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class Student {
    @Setter
    private int id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 100, message = "Age must be at most 100")
    private int age;

    @NotBlank(message = "Major cannot be blank")
    private String major;

    @NotNull(message = "Enrollment date cannot be null")
    private LocalDate enrollmentDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public Student(int id, String name, String gender, int age, String major, LocalDate enrollmentDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.enrollmentDate = enrollmentDate;
    }

    public Student(String name, String gender, int age, String major, LocalDate enrollmentDate) {
        this.id = 0;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.enrollmentDate = enrollmentDate;
    }

    public Student(int id, String name, String gender, int age, String major,
                   LocalDate enrollmentDate, LocalDateTime createdAt, LocalDateTime updatedAt,
                   String createdBy, String updatedBy) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.enrollmentDate = enrollmentDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Student(){

    }


    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // For LocalDate
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;  // For LocalDateTime

        return String.format("Student(id=%d, name=%s, gender=%s, age=%d, major=%s, enrollmentDate=%s, createdAt=%s, updatedAt=%s, createdBy=%s, updatedBy=%s)",
                id, name, gender, age, major,
                enrollmentDate != null ? enrollmentDate.format(dateFormatter) : null,
                createdAt != null ? createdAt.format(dateTimeFormatter) : null,
                updatedAt != null ? updatedAt.format(dateTimeFormatter) : null,
                createdBy, updatedBy);
    }

}