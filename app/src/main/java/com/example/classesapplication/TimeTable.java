package com.example.classesapplication;

public class TimeTable {
    String Date, TeacherName, TimetableImage, cLass;

    public TimeTable() {
    }

    public TimeTable(String date, String teacherName, String timetableImage, String cLass) {
        Date = date;
        TeacherName = teacherName;
        TimetableImage = timetableImage;
        this.cLass = cLass;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTimetableImage() {
        return TimetableImage;
    }

    public void setTimetableImage(String timetableImage) {
        TimetableImage = timetableImage;
    }

    public String getcLass() {
        return cLass;
    }

    public void setcLass(String cLass) {
        this.cLass = cLass;
    }
}
