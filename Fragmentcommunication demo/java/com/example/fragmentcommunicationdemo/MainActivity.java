package com.example.fragmentcommunicationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button1,button2,sendMsgToFragmentBtn1;
    TextView textView;


    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        EdgeToEdge.enable(this);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        textView = findViewById(R.id.textView4);
//        sendMsgToFragmentBtn1 = findViewById(R.id.fragment1Btn);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
//        sendMsgToFragmentBtn1.setOnClickListener(this);

         launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == 101){
                            Intent data = result.getData();
                            if(data!=null){
                                textView.setText("Hey! "+data.getStringExtra("name"));
                            }

                        }

                    }
                });


    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button){
            GotoActivity(MainActivity2.class);
        }

        if(view.getId() == R.id.button2){
            GotoActivity(MainActivity3.class);
        }


    }

    public void GotoActivity(Class<?> activity){
        try{
            Intent intent = new Intent(MainActivity.this,activity);

            launcher.launch(intent);
        }catch (Exception e){
            System.out.println(e);
        }

    }
}