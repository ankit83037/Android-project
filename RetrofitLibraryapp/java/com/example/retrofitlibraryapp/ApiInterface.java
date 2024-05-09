package com.example.retrofitlibraryapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("marvel")
    Call<List<Hero>> getSuperHeroes();
    @POST("/api/users")
    Call<User> postData(@Body User user);
}
