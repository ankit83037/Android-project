package com.example.socketprogrammingpostdataapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button postData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postData = findViewById(R.id.button);

        postData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketClient socketClient = new SocketClient();
                socketClient.makePostRequest();

            }
        });

    }
}