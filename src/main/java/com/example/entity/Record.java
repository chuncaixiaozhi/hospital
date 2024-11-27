package com.example.entity;

import java.io.Serializable;

/**
 * 公告信息表
*/
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    private Integer userId;

    private Integer doctorId;

    /** 创建时间 */
    private String time;
    //医嘱
    private String mdRecord;
    //是否住院
    private String inHospital;
    private String userName;
    private String doctorName;
    //是否住院登记
    private String inRecord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getMdRecord() {
        return mdRecord;
    }

    public void setMdRecord(String mdRecord) {
        this.mdRecord = mdRecord;
    }

    public String getInHospital() {
        return inHospital;
    }

    public void setInHospital(String inHospital) {
        this.inHospital = inHospital;
    }

    public String getInRecord() {
        return inRecord;
    }

    public void setInRecord(String inRecord) {
        this.inRecord = inRecord;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

}
