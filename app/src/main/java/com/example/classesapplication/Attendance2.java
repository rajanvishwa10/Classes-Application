package com.example.classesapplication;

public class Attendance2 {
    String Attendance, Date, StudentName, TeacherName;

    public Attendance2() {
    }

    public Attendance2(String attendance, String date, String studentName, String teacherName) {
        Attendance = attendance;
        Date = date;
        StudentName = studentName;
        TeacherName = teacherName;
    }

    public String getAttendance() {
        return Attendance;
    }

    public void setAttendance(String attendance) {
        Attendance = attendance;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }
}
