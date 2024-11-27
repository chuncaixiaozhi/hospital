package com.example.entity;

import java.io.Serializable;

/**
 * 公告信息表
*/
public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer id;
    private Integer doctorId;
    private Integer num;

    private String week;
    private String doctorName;
    private String DepartmentName;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
