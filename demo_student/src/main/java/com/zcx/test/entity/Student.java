package com.zcx.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable{
    private Integer id;

    private String stuname;

    private String sex;
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date birthdate;

    private String address;

    public Student(Integer id, String stuname, String sex, Date birthdate, String address) {
        this.id = id;
        this.stuname = stuname;
        this.sex = sex;
        this.birthdate = birthdate;
        this.address = address;
    }
    public Student( String stuname, String sex, Date birthdate, String address) {
        this.stuname = stuname;
        this.sex = sex;
        this.birthdate = birthdate;
        this.address = address;
    }

    public Student() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname == null ? null : stuname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", stuname='" + stuname + '\'' +
                ", sex='" + sex + '\'' +
                ", birthdate=" + birthdate +
                ", address='" + address + '\'' +
                '}';
    }
}