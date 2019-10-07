package com.tkadziolka.retrofit.data.model;

public class User {
    private int id;
    private String name;
    private String job;

    public User(int id, String name, String job) {
        this.id = id;
        this.name = name;
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
