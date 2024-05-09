package com.example.externalstorageandhttprequest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button getDataButton;
    ArrayList<File> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataButton = findViewById(R.id.button);

        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);

        }else{
            fetchFileFromExternalStorage();
        }
        
        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFileFromExternalStorage();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("request code", "onRequestPermissionsResult: "+requestCode + permissions[0] + grantResults[0]);
        if(requestCode == 100){
            if( grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fetchFileFromExternalStorage();
            }
        }else{
            System.out.println("permission denied");
            Toast.makeText(this, "Permission is denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchFileFromExternalStorage() {

        File dir = new File(Environment.getExternalStorageDirectory().toString());

//        File dir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        ArrayList<File> arrayList = getFile(dir);
        uploadFile(arrayList);

        System.out.println("dir " +dir);
    }

    private void uploadFile(ArrayList<File> arrayList) {
        if(arrayList.size()>0){
            OkHttpClient okHttpClient = new OkHttpClient();
            String url = "https://api.escuelajs.co/api/v1/files/upload";
//            String url = "https://reqres.in/api/users";
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file",arrayList.get(0).getName(),RequestBody.create(MediaType.parse("text/plain"),arrayList.get(0)))
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
        }else{
            System.out.println("arraylist size <1");
        }

    }

    private ArrayList<File> getFile(File dir) {


        if(dir == null || !dir.exists()){
            System.out.println("not directory");
            return arrayList;
        }
        File[] files = dir.listFiles();
        System.out.println("list of files " +files);
        if(files !=null && files.length>0){
            System.out.println("inside if "+files);
            for(int i=0;i<files.length;i++){
                String fileName = files[i].getName().toLowerCase();
               if(files[i].isFile() && fileName.endsWith(".txt") || fileName.endsWith(".pdf")){
                    System.out.println("adding in arraylist" +files[i].getName());
                    arrayList.add(files[i]);
                }
               else if(files[i].isDirectory()){
                   System.out.println("file.isDirectory "+files[i]);
                   getFile(files[i]);
               }
//                else if(file.isFile() && file.getName().toLowerCase().endsWith(".txt")){
//                    System.out.println(file);
//                }
            }
        }else{
            System.out.println("else "+String.valueOf(files));
        }
        return arrayList;
    }

}