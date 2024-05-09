package com.example.retrofitlibraryapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {
    @SerializedName("name")
    private String name;
    @SerializedName("job")
    private String job;

    private ArrayList<String> list;

    public User(String name, String job,ArrayList<String> list) {
        this.name = name;
        this.job = job;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
