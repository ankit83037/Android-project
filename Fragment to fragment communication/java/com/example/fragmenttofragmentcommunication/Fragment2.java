package com.example.fragmenttofragmentcommunication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment2 extends Fragment {

    TextView tv1;
    String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_2, container, false);

        tv1 = view.findViewById(R.id.tv1);

//        Bundle bundle = getArguments();
//        if (bundle != null){
//            tv1.setText(bundle.getString("key"));
//        }

        tv1.setText(this.message);
        System.out.println(" inside oncreateview");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println(" onViewCreated");

    }

    public void send(String data){

//        tv1.setText(data);
        System.out.println(data);
        this.message = data;
    }

}