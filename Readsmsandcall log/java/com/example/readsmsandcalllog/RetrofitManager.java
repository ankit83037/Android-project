package com.example.readsmsandcalllog;

import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitManager {
    private Retrofit retrofit;
    public RetrofitManager(){
         retrofit = RetrofitClient.getInstance();
    }
    public void uploadSmsMessages(ArrayList<SMSMessage> smsMessageArrayList){
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.uploadSmsMessages(smsMessageArrayList).enqueue(new Callback<ArrayList<SMSMessage> >() {
            @Override
            public void onResponse(Call<ArrayList<SMSMessage> > call, Response<ArrayList<SMSMessage> > response) {
                if(response.isSuccessful()){
                    SMSMessage smsMessage = response.body().get(0);
                    Log.i("response data", "onResponse: "+response.body());
                    System.out.println("response " + smsMessage.getBody() +" "+smsMessage.getSender()+" "+smsMessage.getDate());
                }else {
                    System.out.println("upload failed");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SMSMessage> > call, Throwable t) {
                System.out.println("error in connection " + t.getMessage());
            }
        });
    }
}
