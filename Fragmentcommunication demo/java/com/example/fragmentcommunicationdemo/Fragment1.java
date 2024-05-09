package com.example.fragmentcommunicationdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    TextView msgTextView;
    Button msgToActivity3Btn;
    Button msgToFragment2Btn;

    public interface PassData{
        public void dataFromFragment1(String data);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_1, container, false);
         msgTextView = (TextView) view.findViewById(R.id.msgTextView);
         msgToActivity3Btn = view.findViewById(R.id.msgToActivity3Btn);
        msgToFragment2Btn = view.findViewById(R.id.msgToFragment2Btn);

//         Bundle bundle = getArguments();
//
//         if(bundle!=null){
//             String message = bundle.getString("msg");
//             System.out.println("inside fragment1");
//             msgTextView.setText(message);
//         }

         msgToActivity3Btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                MainActivity3 activity3 = (MainActivity3) getActivity();
                activity3.setTextMessage("Message from fragment1");
             }
         });

        msgToFragment2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("msg1","message from Fragment1");
//                Fragment2 fragment2 = new Fragment2();
//                fragment2.setArguments(bundle);

                MainActivity3 activity = (MainActivity3) requireActivity();
                activity.dataFromFragment1("Data from fragment1");


            }
        });

        return view;
    }

    public void updateText(String data){
        msgTextView.setText(data);
    }


}