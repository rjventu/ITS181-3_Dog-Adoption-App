package com.example.appdev3_project.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Dog implements Serializable {

    private Long id;
    private String name, gender, img;
    private int age;
    @SerializedName("description")
    private String bio;
    @SerializedName("vacc")
    private boolean vaccination;
    @SerializedName("ster")
    private boolean sterilization;

    public Dog() { }

    public Dog(Long id, String name, String gender, int age, boolean vaccination, boolean sterilization, String img, String bio) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.vaccination = vaccination;
        this.sterilization = sterilization;
        this.img = img;
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

    public String getImg() { return img; }

    public void setImg(String img) { this.img = img; }

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

