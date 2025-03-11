package com.example.appdev3_project.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Adoption implements Serializable {
    private Long id;
    private String status;
    private LocalDateTime datetime;
    private User user;
    private Dog dog;

    public Adoption(String status, LocalDateTime datetime, Dog dog) {
        this.status = status;
        this.datetime = datetime;
//        this.user = user;
        this.dog = dog;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Dog getDog() {
        return dog;
    }
    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
