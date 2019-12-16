package com.zcx.test.service.impl;

import com.zcx.test.dao.StudentMapper;
import com.zcx.test.entity.Student;
import com.zcx.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentMapper mapper;

    public List<Student> listAll() {

        return mapper.selectAll();
    }

    public void insert(Student student) {
        mapper.insertSelective(student);
    }

    public void update(Student student) {

        mapper.updateByPrimaryKeySelective(student);
    }

    public void delete(int id) {
        mapper.deleteByPrimaryKey(id);
    }

    public Student findOne(int id) {
        return mapper.selectByPrimaryKey(id);
    }
}
