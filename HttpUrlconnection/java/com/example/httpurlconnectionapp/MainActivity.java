package com.example.httpurlconnectionapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private  static Handler handler;
    Button sendHttpRequestButton,loadImageButton,postDataButton;
    TextView showResponseTextView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendHttpRequestButton = findViewById(R.id.sendHttpButton);
        showResponseTextView = findViewById(R.id.showResponseTextView);
        loadImageButton = findViewById(R.id.loadImage);
        imageView = findViewById(R.id.imageView);
        postDataButton = findViewById(R.id.postData);

        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "https://images.unsplash.com/photo-1613226505855-999302e0c08d?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8cGl4ZWx8ZW58MHx8MHx8fDA%3D";
                String url = "https://picsum.photos/v2/list";
                HttpRequest httpRequest = new HttpRequest();
                httpRequest.sendHttpRequest(url,handler,"image");
            }
        });

        sendHttpRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.geeksforgeeks.org/android-tutorial/";
                HttpRequest httpRequest = new HttpRequest();
                httpRequest.sendHttpRequest(url,handler,"text");
            }
        });

        postDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest httpRequest = new HttpRequest();
                httpRequest.postRequest();
            }
        });


        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 101){
                    Bundle bundle = msg.getData();
                    if (bundle!=null){
                        String response = bundle.getString("response");
                        showResponseTextView.setMovementMethod(new ScrollingMovementMethod());
                        showResponseTextView.setText(Html.fromHtml(response));
                    }
                }
                if (msg.what == 102){

                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                    Log.i("inside 102 msg.what", "handleMessage: bitmap image"+bitmap);

                }
            }
        };
    }

}