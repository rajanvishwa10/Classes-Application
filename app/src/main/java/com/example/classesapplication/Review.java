package com.example.classesapplication;

class Review {
    String Rating, StudentName, Suggestions, TeacherName, Date;

    public Review(String rating, String studentName, String suggestions, String teacherName, String date) {
        Rating = rating;
        StudentName = studentName;
        Suggestions = suggestions;
        TeacherName = teacherName;
        Date = date;
    }

    public Review() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getSuggestions() {
        return Suggestions;
    }

    public void setSuggestions(String suggestions) {
        Suggestions = suggestions;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }
}
