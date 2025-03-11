package com.example.appdev3_project.model;

import java.io.Serializable;

public class Dog implements Serializable {

    private Long id;
    private String name, gender, bio;
    private int age, imageResId;
    private boolean vaccination, sterilization;

    public Dog(String name, String gender, int age, boolean vaccination, boolean sterilization, int imageResId, String bio) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.vaccination = vaccination;
        this.sterilization = sterilization;
        this.imageResId = imageResId;
        this.bio = bio;
    }

    // Getters
    public String getName() { return name; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public boolean isVaccinated() { return vaccination; }
    public boolean isSterilized() { return sterilization; }
    public int getImageResId() { return imageResId; }
    public String getBio() { return bio; }

    // Setters
    public void setAge(int age) { this.age = age; }
    public void setVaccination(boolean vaccination) { this.vaccination = vaccination; }
    public void setSterilization(boolean sterilization) { this.sterilization = sterilization; }
}

