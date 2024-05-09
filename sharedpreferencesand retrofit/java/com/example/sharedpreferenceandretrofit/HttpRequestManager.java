package com.example.sharedpreferenceandretrofit;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequestManager {
    Context context;

    public HttpRequestManager(Context context) {
        this.context = context;
    }

    private JSONObject getFromSharedPreferences() {

        SharedPreferences sh = context.getSharedPreferences("LoginData",MODE_PRIVATE);
        String username = sh.getString("username","");
        String password = sh.getString("password","");

        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("username",username);
            jsonData.put("password",password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonData;
    }

    public void postRequest(){
        JSONObject jsonObject = getFromSharedPreferences();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonObject),JSON);

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

                Log.i("response",myResponse);

                if(response.isSuccessful()){
//                    ContextCompat.getMainExecutor(context).execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "Post method executed successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }

            }
        });
    }


}
