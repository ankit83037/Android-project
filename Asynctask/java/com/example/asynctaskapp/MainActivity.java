package com.example.asynctaskapp;

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

    Button getButton;
    TextView showContentTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getButton = findViewById(R.id.button);
        showContentTextView = findViewById(R.id.textView);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean generateRandomNo = true;
                AsyncTaskManager asyncTaskManager = new AsyncTaskManager(showContentTextView);
                asyncTaskManager.execute(generateRandomNo);
            }
        });

    }
}