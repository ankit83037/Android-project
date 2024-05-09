package com.example.okhttplibraryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button getDataButton,postDataButton;
    TextView showDataTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataButton = findViewById(R.id.button);
        showDataTextView = findViewById(R.id.textView);
        postDataButton = findViewById(R.id.button2);

        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://reqres.in/api/users/2";
                OkHttpClientManager okHttpClientManager = new OkHttpClientManager(MainActivity.this,showDataTextView,url);
                okHttpClientManager.makeRequest();
            }
        });

        postDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClientManager okHttpClientManager = new OkHttpClientManager(MainActivity.this,showDataTextView,"");
                okHttpClientManager.postRequest(MainActivity.this);
            }
        });
    }
}