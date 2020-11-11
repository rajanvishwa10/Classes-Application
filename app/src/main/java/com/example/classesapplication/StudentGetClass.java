package com.example.classesapplication;

class StudentGetClass {
    String name, profileImage, cLass, guardianName, mobile, email;

    public StudentGetClass() {

    }

    public StudentGetClass(String name, String profileImage, String cLass, String guardianName, String mobile, String email) {
        this.name = name;
        this.profileImage = profileImage;
        this.cLass = cLass;
        this.guardianName = guardianName;
        this.mobile = mobile;
        this.email = email;
    }

    public String getcLass() {
        return cLass;
    }

    public void setcLass(String cLass) {
        this.cLass = cLass;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
