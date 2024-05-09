package com.example.accessingcontactdetails;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView showContactDetails;
    Button loadContactBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showContactDetails = findViewById(R.id.showContactDetails);
        loadContactBtn = findViewById(R.id.loadButton);

        loadContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},100);
                    }
                }else{
                    readContacts();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                readContacts();
            }
        }

    }

        public void readContacts(){
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        if(cursor !=null && cursor.getColumnCount()>0){
            StringBuilder stringBuilder = new StringBuilder();
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                stringBuilder.append(name);
                stringBuilder.append("\n"+phone+"\n");
            }
            showContactDetails.setText(stringBuilder);
        }
    }
}