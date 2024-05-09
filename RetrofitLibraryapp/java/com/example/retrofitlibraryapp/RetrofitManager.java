package com.example.retrofitlibraryapp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    String url = "https://simplifiedcoding.net/demos/";

    public RetrofitManager(){

    }
    public void makeRequest(Handler handler){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface retrofitApi = retrofit.create(ApiInterface.class);
        Call<List<Hero>> call = retrofitApi.getSuperHeroes();
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> heroList = response.body();
                String heroName = heroList.get(0).getSuperHeroName();

                Message message = Message.obtain();
                message.what = 101;
                message.obj = heroName;
                handler.sendMessage(message);
                Log.i("data", "onResponse: "+heroName);
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Log.i("not connected", "onFailure: ");
            }
        });
    }

    public void postRequest(Context context){
        ArrayList<String> al = new ArrayList<>();
        al.add("Android");
        al.add("RE");

//
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://reqres.in/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.postData(new User("Raju","Manage",al)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if (response.code() == 201){
                        User user = response.body();
                        Log.i("response", "onResponse: "+user.getList());
                        Toast.makeText(context, "post method executed successfully \n"+user.getName()+" "+user.getList(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("onFailure", "onFailure: "+t.getMessage());
            }
        });
    }
}
