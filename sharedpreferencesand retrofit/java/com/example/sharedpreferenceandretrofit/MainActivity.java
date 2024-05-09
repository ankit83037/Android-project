package com.example.sharedpreferenceandretrofit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText usernameEdt,passwordEdt;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEdt = findViewById(R.id.editTextText);
        passwordEdt = findViewById(R.id.editTextText2);
        loginBtn = findViewById(R.id.button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =usernameEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                saveInSharedPreference(name,password);
                HttpRequestManager httpRequestManager = new HttpRequestManager(MainActivity.this);
                httpRequestManager.postRequest();
            }
        });

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        String name =usernameEdt.getText().toString();
//        String password = passwordEdt.getText().toString();
//        saveInSharedPreference(name,password);
//    }

    private void saveInSharedPreference(String name, String password) {

        SharedPreferences sharedPreferences = getSharedPreferences("LoginData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",name);
        editor.putString("password",password);
        editor.apply();
    }


}