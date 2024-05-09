package com.example.readsmsandcalllog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/api/users")
    Call<ArrayList<SMSMessage>> uploadSmsMessages(@Body ArrayList<SMSMessage> smsMessages);
}
