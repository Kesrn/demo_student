package com.zcx.test.service;

import com.zcx.test.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> listAll();

    void insert(Student student);

    void delete(int id);

    Student findOne(int id);

    void update(Student student);
}
