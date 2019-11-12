package com.csiqi.utils.test;

/**
 * Created by cl_kd-user47823 on 2019/11/12.
 */
public class Student {
    private String studentId;
    private String name;
    private Object stationerys;
    private String sex;
    private int age;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Object getStationerys() {
        return stationerys;
    }

    public void setStationerys(Object stationerys) {
        this.stationerys = stationerys;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(String studentId,String name, String sex, Object stationerys, int age) {
        this.studentId = studentId;
        this.name = name;
        this.stationerys = stationerys;
        this.sex = sex;
        this.age = age;
    }
}
