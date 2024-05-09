package com.example.note_takingapplication;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addNoteButton;

    ListView listView;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNoteButton = findViewById(R.id.addNoteButton);

        listView = findViewById(R.id.listview);

        broadcastReceiver = new MyBroadcastReceiver();

//        SyncServiceSchedular.scheduleSyncService(this);

        ArrayList<Note> arrayList = loadNoteDetails() ;
        CustomAdapter adapter = new CustomAdapter(this,arrayList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),NoteDetails.class);
                intent.putExtra("noteObject", (Parcelable) arrayList.get(position));
                startActivity(intent);
            }
        });

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNote.class);
                startActivity(intent);
            }
        });

        startDataSyncning();



    }

    private void startDataSyncning() {
        Intent intent = new Intent(MainActivity.this,DataSyncService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ArrayList<Note> arrayList = loadNoteDetails();

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public ArrayList<Note> loadNoteDetails(){
        ArrayList<Note> arrayList = new ArrayList<>();

        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI,null,null,null,null);

        if(cursor.moveToFirst()){

            while (!cursor.isAfterLast()){
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));

                System.out.println("id in loadNoteDetails " + id + title + description);
                Note note = new Note(id,title,description);
                arrayList.add(note);
                cursor.moveToNext();

            }
        }else{
//            showNoteTextView.setText("no record found");
        }

        return arrayList;
    }
    public void onClickShowDetails(View view){


//        showNoteTextView.setText(arrayList.get(7).getTitle());

    }
}