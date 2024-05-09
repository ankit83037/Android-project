package com.example.okhttplibraryapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpClientManager {
    Activity activity;
    TextView textView;
    String url;

    public OkHttpClientManager(){

    }
    public OkHttpClientManager(Activity activity, TextView textView, String url){
        this.activity = activity;
        this.textView = textView;
        this.url = url;
    }

    public void makeRequest(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String myResponse = response.body().string();
                activity.runOnUiThread(new Runnable(){
                    public void run(){
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            textView.setText(jsonObject1.getString("email")+ "\n"+jsonObject1.getString("first_name")+"\n"+
                                    jsonObject1.getString("avatar"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
            }
        });
    }

    public void postRequest(Context context){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("1");
        jsonArray.put("2");
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("name","Raju");
            jsonData.put("job",jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonArray),JSON);

        Request request = new Request.Builder().url("https://reqres.in/api/users").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("failed to post data", "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i("success", "onResponse: "+response.code() + response);
                String myResponse = response.body().string();
                try {
                    JSONArray jsonArray1 = new JSONArray(myResponse);
                    for (int i=0;i<jsonArray1.length();i++){
                        System.out.println(jsonArray1.get(i));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.i("response",myResponse);
//                Toast.makeText(activity.getBaseContext(), "Post method executed successfully \n"+response.body(), Toast.LENGTH_SHORT).show();

                if(response.isSuccessful()){
                    ContextCompat.getMainExecutor(context).execute(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Post method executed successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}
