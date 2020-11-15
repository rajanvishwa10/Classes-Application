package com.example.classesapplication;

public class Assignments {
    String Assignment_Details, Assignment_Image, Date, Assignment_Pdf;

    public Assignments() {
    }

    public Assignments(String assignment_Details, String assignment_Image, String assignment_Pdf, String date) {
        Assignment_Details = assignment_Details;
        Assignment_Image = assignment_Image;
        Assignment_Pdf = assignment_Pdf;
        Date = date;
    }

    public String getAssignment_Details() {
        return Assignment_Details;
    }

    public void setAssignment_Details(String assignment_Details) {
        Assignment_Details = assignment_Details;
    }

    public String getAssignment_Image() {
        return Assignment_Image;
    }

    public void setAssignment_Image(String assignment_Image) {
        Assignment_Image = assignment_Image;
    }

    public String getAssignment_Pdf() {
        return Assignment_Pdf;
    }

    public void setAssignment_Pdf(String assignment_Pdf) {
        Assignment_Pdf = assignment_Pdf;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
