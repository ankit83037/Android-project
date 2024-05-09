package com.example.fragmenttofragmentcommunication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment1 extends Fragment {

    Button send;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        send = view.findViewById(R.id.send_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key","value");
                Fragment2 fragment2 = new Fragment2();

//                fragment2.setArguments(bundle);

                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,fragment2);
                ft.commit();

                fragment2.send("Hi there!!");
            }
        });

        return  view;
    }
}