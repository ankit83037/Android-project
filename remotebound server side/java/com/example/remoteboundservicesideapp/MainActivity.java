package com.example.remoteboundservicesideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.badge.BadgeUtils;

public class MainActivity extends AppCompatActivity {

    Button startServices,stopServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startServices = findViewById(R.id.start_service);
        stopServices = findViewById(R.id.stop_service);

        startServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RemoteBoundService.class);
                startService(intent);
                Toast.makeText(MainActivity.this, "service started", Toast.LENGTH_SHORT).show();
            }
        });

        stopServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RemoteBoundService.class);
                stopService(intent);
                Toast.makeText(MainActivity.this, "service stopped", Toast.LENGTH_SHORT).show();
            }
        });

    }
}