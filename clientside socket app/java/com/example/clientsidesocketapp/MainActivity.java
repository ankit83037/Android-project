package com.example.clientsidesocketapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Button connectToServerButton;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectToServerButton = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        connectToServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToServer();
            }
        });

    }

    private void connectToServer() {
        String serverName = "127.1.2.10";
        int serverPort = 5000;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader bufferedReader;
                    Socket socket = new Socket(serverName, serverPort);

                    System.out.println("client is running");
//                    getting message from server
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String response = bufferedReader.readLine();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("client side " + response);
                            textView.setText(response);
                        }
                    });

//                    sending message to server
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("Hello from client");
                    printWriter.flush();
                    System.out.println("message sent through client");

                }catch (Exception e){
                    System.out.println("error "+e.getMessage());


                }
            }
        }).start();
    }
}