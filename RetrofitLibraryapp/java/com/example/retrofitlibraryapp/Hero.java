package com.example.retrofitlibraryapp;

import com.google.gson.annotations.SerializedName;

public class Hero {
    @SerializedName("name")
    private String superHeroName;
    public Hero(String name) {
        this.superHeroName = name;
    }

    public String getSuperHeroName() {
        return superHeroName;
    }

    public void setSuperHeroName(String message) {
        this.superHeroName = superHeroName;
    }

}
