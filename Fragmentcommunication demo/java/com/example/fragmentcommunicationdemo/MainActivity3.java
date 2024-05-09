package com.example.fragmentcommunicationdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity3 extends AppCompatActivity implements Fragment2.DataPassInterface, Fragment1.PassData {

    Button msgBtn,fragment2Btn;
    TextView showMessageTextView;
    Fragment2 fragment2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);

        msgBtn = findViewById(R.id.sendMsgBtn);
        fragment2Btn = findViewById(R.id.fragment2Btn);
        showMessageTextView = findViewById(R.id.showMessage);

        fragment2 = new Fragment2();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frameLayout,fragment2,"fragment2");
        ft.commit();

        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("msg","message from activity1");


                Fragment1 fragment1 = (Fragment1) getSupportFragmentManager().findFragmentById(R.id.fragment1);
                if(fragment1 !=null){
                    fragment1.updateText("message from activity1");

                }else{
                     fragment1 = new Fragment1();
                     fragment1.setArguments(bundle);
                }


//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.commit();
            }
        });

        fragment2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("msg","message from activity3");

                Fragment2 fragment2 = new Fragment2();
                fragment2.setArguments(bundle);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,fragment2,"fragment2");
                ft.commit();

                // setArguments pass data while fragment instantiation
                // if fragment is already loaded or attached then user defined method should be used to pass data.
                // if new fragment is loading or attaching to activity then use setArguments( ).

//                Fragment2 fragment2 = (Fragment2) getSupportFragmentManager().findFragmentByTag("fragment2");
//                if(fragment2 != null){
//                    fragment2.setArguments(bundle);
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.frameLayout,fragment2);
//                    ft.commit();
//
//
//                    System.out.println("fragment2Btn");
//                }



//                Fragment2 fragment2 = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment2);
//                fragment2.setArguments(bundle);


            }
        });




    }

    public void setTextMessage(String message) {
        showMessageTextView.setText(message);
    }

    public void onDataPass(String data){
        showMessageTextView.setText(data);
    }

    public void dataFromFragment1(String data){
        Fragment2 fragment2 = (Fragment2) getSupportFragmentManager().findFragmentByTag("fragment2");
        if(fragment2 != null){
            fragment2.showData(data);
        }

    }
}