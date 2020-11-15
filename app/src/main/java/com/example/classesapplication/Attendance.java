package com.example.classesapplication;

public class Attendance {
    public String name, email, cLass,profileImage;

    public Attendance() {
    }

    public Attendance(String name, String email, String cLass, String profileImage) {
        this.name = name;
        this.email = email;
        this.cLass = cLass;
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getcLass() {
        return cLass;
    }

    public void setcLass(String cLass) {
        this.cLass = cLass;
    }
}
