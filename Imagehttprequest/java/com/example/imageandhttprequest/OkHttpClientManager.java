package com.example.imageandhttprequest;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpClientManager {
    InputStream inputStream;
    byte[] byteArr;
    public void upload(Context context, Uri selectedImageUri){
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "https://api.escuelajs.co/api/v1/files/upload";
//            String url = "https://reqres.in/api/users";

        try {
            inputStream = context.getContentResolver().openInputStream(selectedImageUri);
        } catch (FileNotFoundException e) {
            System.out.println("inputstream error "+inputStream);
        }

        try {
             byteArr = readBytes(inputStream);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file","image.png",RequestBody.create(byteArr,MediaType.parse("image/png")))
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("failed " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                System.out.println("response "+response.body().string());
            }
        });
    }

    private byte[] readBytes(InputStream inputStream) throws IOException{
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1){
            byteBuffer.write(buffer,0,len);
        }
        return byteBuffer.toByteArray();
    }
}
