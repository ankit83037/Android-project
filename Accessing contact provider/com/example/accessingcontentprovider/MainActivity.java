package com.example.accessingcontentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Uri CONTENT_URI = Uri.parse("content://com.demo.user.provider/users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowDetails(View view){
        TextView textView = findViewById(R.id.res);

        Cursor cursor = getContentResolver().query(CONTENT_URI,null,null,null,null);

        if(cursor != null && cursor.moveToFirst()){
            StringBuilder strBuild = new StringBuilder();
            while(!cursor.isAfterLast()){
                strBuild.append("\n"+cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")));
                cursor.moveToNext();
            }
            textView.setText(strBuild);
        }
        else{
            textView.setText("No records found");
        }
    }
}