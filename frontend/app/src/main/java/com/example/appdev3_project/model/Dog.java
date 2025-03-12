package com.example.appdev3_project.model;

import java.io.Serializable;

public class Dog implements Serializable {

    private Long id;
    private String name, gender, bio;
    private int age, imageResId;
    private boolean vaccination, sterilization;

    public Dog() { }

    public Dog(Long id, String name, String gender, int age, boolean vaccination, boolean sterilization, int imageResId, String bio) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.vaccination = vaccination;
        this.sterilization = sterilization;
        this.imageResId = imageResId;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public boolean isVaccinated() {
        return vaccination;
    }

    public void setVaccination(boolean vaccination) {
        this.vaccination = vaccination;
    }

    public boolean isSterilized() {
        return sterilization;
    }

    public void setSterilization(boolean sterilization) {
        this.sterilization = sterilization;
    }
}

