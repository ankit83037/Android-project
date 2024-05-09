package com.example.retrofitlibraryapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button getDataButton,postButton;
    TextView textView;

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==101){
                String heroName = (String) msg.obj;
                Log.i("message", "handleMessage: "+heroName);
                textView.setText(heroName);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataButton = findViewById(R.id.button);
        postButton = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);

        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitManager retrofitManager = new RetrofitManager();
                retrofitManager.makeRequest(handler);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitManager retrofitManager = new RetrofitManager();
                retrofitManager.postRequest(MainActivity.this);
            }
        });
    }


}