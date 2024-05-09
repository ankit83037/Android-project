package com.example.note_takingapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class AddNote extends AppCompatActivity {

    EditText titleEditText, descriptionEditText;
    Button addNoteButton;
    LocalBroadcastReceiver localBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleEditText = findViewById(R.id.title_editText);
        descriptionEditText = findViewById(R.id.description_editText);
        addNoteButton = findViewById(R.id.add_note_button);
        localBroadcastReceiver = new LocalBroadcastReceiver();


        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(MyContentProvider.title_Col,title);
                contentValues.put(MyContentProvider.description_Col,description);

                getContentResolver().insert(MyContentProvider.CONTENT_URI,contentValues);

//                Toast.makeText(getBaseContext(),"New Record Inserted"+title,Toast.LENGTH_LONG).show();

                Intent intent = new Intent("com.demo.localBroadcast");
                LocalBroadcastManager.getInstance(AddNote.this).sendBroadcast(intent);

                startActivity(new Intent(AddNote.this,MainActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.demo.localBroadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(localBroadcastReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadcastReceiver);
    }
}