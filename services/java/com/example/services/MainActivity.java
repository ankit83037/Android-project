package com.example.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button startedServices,localBoundServices,remoteBoundServices;
    TextView randomNumberTextView;
    private LocalBoundService localBoundService;
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startedServices = findViewById(R.id.button);
        localBoundServices = findViewById(R.id.button2);
        remoteBoundServices = findViewById(R.id.button3);
        randomNumberTextView = findViewById(R.id.randomNumbertextView);

    }

    public void onClickStartedServices(View v){
        startService(new Intent(this,StartedServices.class));
        Toast.makeText(this, "Background service is started", Toast.LENGTH_SHORT).show();
    }

    public void onClickLocalBoundServices(View v){

        Intent intent = new Intent(this,LocalBoundService.class);

         ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LocalBoundService.LocalBinder binder = (LocalBoundService.LocalBinder) service;
                localBoundService  = (LocalBoundService) binder.getService();
                isBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBound = false;
            }
        };

        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

//        if(isBound){
//            int num = localBoundService.getRandomNumber();
//            randomNumberTextView.setText(""+num);
//        }


//        using callback interface

//        if (isBound){
//            LocalBoundService.RandomNumberCallback callback = new LocalBoundService.RandomNumberCallback(){
//                public void onRandomNumberGenerated(final int randomNumber){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            randomNumberTextView.setText(""+randomNumber);
//                        }
//                    });
//                }
//            };
//            localBoundService.getRandomNumberInBackground(callback);
//        }

        if (isBound){
            Handler handler = new Handler(getMainLooper());
            localBoundService.getRandomNumberInBackground(handler,randomNumberTextView);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, StartedServices.class));
    }
}