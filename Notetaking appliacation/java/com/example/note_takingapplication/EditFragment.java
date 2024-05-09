package com.example.note_takingapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.badge.BadgeUtils;

public class EditFragment extends Fragment {

    EditText titleEditText,descEditText;
    Button saveButton;

    String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        titleEditText = view.findViewById(R.id.title_et_fragment);
        descEditText = view.findViewById(R.id.desc_et_fragment);
        saveButton = view.findViewById(R.id.save_Button);

        Bundle bundle = getArguments();

        if(bundle != null){
            id = bundle.getString("id");
//            titleEditText.setText(bundle.getString("title"));
//            descEditText.setText(bundle.getString("description"));
        }
//        Log.i("bundle", "onCreateView: "+bundle.getString("title"));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String description = descEditText.getText().toString();

                boolean update = false;

                ContentValues values = new ContentValues();
                if(!title.isEmpty()){
                    values.put(MyContentProvider.title_Col,title);
                    update = true;
                }
                if (!description.isEmpty()){
                    values.put(MyContentProvider.description_Col,description);
                    update = true;
                }

                if (update){
                    try {
                        int count = getContext().getContentResolver().update(MyContentProvider.CONTENT_URI,values,MyContentProvider.id_Col+"=?",new String[]{id});
                        Toast.makeText(getContext(), " " + count + "Note updated successfully", Toast.LENGTH_LONG).show();
                        Log.i("id of row updated", "onClick: id" + id);


                        Intent intent = new Intent(getContext(),MainActivity.class);
                        startActivity(intent);

                    }catch (Exception e){
                        Log.i("update table", "onClick: " + e);
                    }
                }else{
                    Toast.makeText(getContext(), "Please enter in the field to save the note", Toast.LENGTH_LONG).show();
                }


            }
        });

        return view;
    }
}