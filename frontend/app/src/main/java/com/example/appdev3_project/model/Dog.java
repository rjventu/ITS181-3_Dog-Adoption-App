package com.example.appdev3_project.model;

public class Dog {

    // add id
    private String name;
    private String gender;
    private String age; // change to int
    private String vaccination; // change to bool
    private String sterilization; // change to bool
    private int imageResId; // Resource ID for image
    private String bio;

    public Dog(String name, String gender, String age, String vaccination, String sterilization, int imageResId, String bio) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.vaccination = vaccination;
        this.sterilization = sterilization;
        this.imageResId = imageResId;
        this.bio = bio;
    }

    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getAge() { return age; }
    public String getVaccination() { return vaccination; }
    public String getSterilization() { return sterilization; }
    public int getImageResId() { return imageResId; }
    public String getBio() { return bio; }
}
