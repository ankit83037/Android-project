package com.example.remoteboundclientsideapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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

    Button bindServices,unBindServices,getRandomNuber;
    TextView textView;
    Messenger randomNumberRequestMessanger,randomNumberReceiveMessanger;
    boolean isBound;

    private class ReceiveRandomNumberHandler extends Handler{
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    int randomNumber = msg.arg1;
                    textView.setText(randomNumber);
                    break;
                default:

            }
            super.handleMessage(msg);
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            randomNumberRequestMessanger = new Messenger(service);
            randomNumberReceiveMessanger = new Messenger(new ReceiveRandomNumberHandler());
            isBound = true;
            Toast.makeText(MainActivity.this, "callback response - service is bounded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            randomNumberRequestMessanger = null;

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindServices = findViewById(R.id.button);
        unBindServices = findViewById(R.id.button2);
        getRandomNuber = findViewById(R.id.button3);
        textView = findViewById(R.id.textView2);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.remoteboundservicesideapp","com.example.remoteboundservicesideapp.RemoteBoundService"));

        bindServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
                Toast.makeText(MainActivity.this, "bind service is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        unBindServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
                isBound = false;
            }
        });

        getRandomNuber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBound){
                    Message requestMessage = Message.obtain(null,0);
                    requestMessage.replyTo = randomNumberReceiveMessanger;
                    try {
                        randomNumberRequestMessanger.send(requestMessage);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(MainActivity.this, "service is not bounded", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}