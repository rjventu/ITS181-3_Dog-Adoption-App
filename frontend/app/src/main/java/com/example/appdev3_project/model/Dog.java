package com.example.appdev3_project.model;

public class Dog { // @Ruby change this if u want
    private String name;
    private String gender;
    private String age;
    private String vaccination;
    private String sterilization;
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
