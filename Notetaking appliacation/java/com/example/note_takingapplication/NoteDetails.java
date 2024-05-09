package com.example.note_takingapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NoteDetails extends AppCompatActivity {

    TextView noteTitleTV,noteDetailsTV;
    Button editButton;
    String title,description,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        noteTitleTV = findViewById(R.id.NoteTitleTV);
        noteDetailsTV = findViewById(R.id.NoteDetailsTV);
        editButton = findViewById(R.id.edit_button);
        Note note = getIntent().getParcelableExtra("noteObject");
        id = note.getId();
        title = note.getTitle();
        description = note.getDetails();

        noteTitleTV.setText(title);
        noteDetailsTV.setText(description);

        System.out.println("Note details " + id);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment();
                editButton.setVisibility(View.GONE);
            }
        });

    }

    private void loadFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
//        bundle.putString("title",title);
//        bundle.putString("description",description);

        EditFragment fragment = new EditFragment();
        fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.framelayout,fragment);
        ft.commit();
    }
}