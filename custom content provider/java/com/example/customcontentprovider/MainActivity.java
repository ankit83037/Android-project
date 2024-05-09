package com.example.customcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }
//        @Override
//        public boolean onTouchEvent(MotionEvent event){
//
//        }

        public void onClickAddDetails(View view){
            ContentValues values = new ContentValues();
            values.put(MyContentProvider.name, ((EditText)findViewById(R.id.textName)).getText().toString());

            getContentResolver().insert(MyContentProvider.CONTENT_URI,values);

            Toast.makeText(getBaseContext(), "New Record Inserted",Toast.LENGTH_LONG).show();
        }

        public void onClickShowDetails(View view){
            TextView textView = (TextView) findViewById(R.id.res);

            Cursor cursor = getContentResolver().query(Uri.parse("content://com.demo.user.provider/users"),null,null,null,null);

            if(cursor.moveToFirst()){
                StringBuilder strBuild = new StringBuilder();
                while (!cursor.isAfterLast()){
                    strBuild.append("\n"+cursor.getString(cursor.getColumnIndex("id"))+"-"+cursor.getString(cursor.getColumnIndex("name")));
                    cursor.moveToNext();
                }
                textView.setText(strBuild);
            }else{
                textView.setText("No records found");
            }
        }

}