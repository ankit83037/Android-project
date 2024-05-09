package com.example.fragmentcommunicationdemo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Fragment2 extends Fragment {
    TextView msgTextView;
    Button msgToActivity3Btn;
    DataPassInterface dataPassInterface;


    public interface DataPassInterface{
        void onDataPass(String data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.dataPassInterface = (MainActivity3)context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        msgTextView = view.findViewById(R.id.fragment2TextView);
        msgToActivity3Btn = view.findViewById(R.id.msgToActivity3Btn);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            String message = bundle.getString("msg");
            msgTextView.setText(message);
        }else{
            msgTextView.setText("");
        }

//        if(bundle !=null){
//            String message1 = bundle.getString("msg1");
//            msgTextView.setText(message1);
//        }

        msgToActivity3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPassInterface.onDataPass("Message from fragment 2");
            }
        });
        return view;
    }

    public void showData(String data){
        msgTextView.setText(data);
    }
}