package com.example.student.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Setter
@Getter
public class StudentQuery {
    // 基础查询条件
    private Integer id;
    private String name;
    private Integer ageMin;
    private Integer ageMax;
    private String gender;
    private String major;
    private LocalDate enrollmentDate;

    // 排序条件
    private String sortBy;          // 排序字段
    private boolean ascending;      // 是否升序

    // 分页参数
    private Integer pageNum;        // 页码
    private Integer pageSize;       // 每页数量

    // 时间范围
    private Date createTimeStart;   // 创建时间起始
    private Date createTimeEnd;     // 创建时间结束

    // 构建器模式（Builder Pattern）
    public static class Builder {
        private final StudentQuery query = new StudentQuery();

        public Builder id(int id) {
            query.id = id;
            return this;
        }

        public Builder name(String name) {
            query.name = name;
            return this;
        }

        public Builder ageMin(Integer ageMin) {
            query.ageMin = ageMin;
            return this;
        }

        public Builder ageMax(Integer ageMax) {
            query.ageMax = ageMax;
            return this;
        }

        public Builder gender(String gender) {
            query.gender = gender;
            return this;
        }

        public Builder major(String major) {
            query.major = major;
            return this;
        }

        public Builder enrollmentDate(LocalDate enrollmentDate) {
            query.enrollmentDate = enrollmentDate;
            return this;
        }

        public Builder sortBy(String sortBy) {
            query.sortBy = sortBy;
            return this;
        }

        public Builder ascending(boolean ascending) {
            query.ascending = ascending;
            return this;
        }

        public Builder pageNum(Integer pageNum) {
            query.pageNum = pageNum;
            return this;
        }

        public Builder pageSize(Integer pageSize) {
            query.pageSize = pageSize;
            return this;
        }

        public Builder createTimeStart(Date createTimeStart) {
            query.createTimeStart = createTimeStart;
            return this;
        }

        public Builder createTimeEnd(Date createTimeEnd) {
            query.createTimeEnd = createTimeEnd;
            return this;
        }

        public StudentQuery build() {
            return query;
        }
    }

    // 静态方法创建Builder实例
    public static Builder builder() {
        return new Builder();
    }
}