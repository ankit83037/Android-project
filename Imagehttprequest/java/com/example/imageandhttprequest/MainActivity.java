package com.example.imageandhttprequest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button pickImageButton;
    ImageView imageView;
    Boolean isPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pickImageButton = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);

        requestPermission();

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,1);
                }else{
                    requestPermission();
                }
            }
        });

    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }else{
            isPermissionGranted = true;
            System.out.println("inside else");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                isPermissionGranted = true;
            }
        }else{
            isPermissionGranted =false;
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                Uri selectedImageUri = intent.getData();
                System.out.println(selectedImageUri);
                imageView.setImageURI(selectedImageUri);
                String selectedImagePath = getPath(selectedImageUri);

//                String selectedImagePath = selectedImageUri.getPath();
                System.out.println(selectedImagePath);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                OkHttpClientManager okHttpClientManager = new OkHttpClientManager();
                okHttpClientManager.upload(MainActivity.this,selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri){
        try{

            System.out.println("uri "+ uri);
        System.out.println("inside getpath");
        if(uri == null){
            System.out.println("inside getPath "+uri);
            return null;
        }

        String[] projection = {MediaStore.Images.Media.DATA};
        System.out.println("after projection");
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        System.out.println("after curssor" + cursor);
        if(cursor != null &&  cursor.moveToFirst() ){
            String path;
                int column_index = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(column_index);
                System.out.println(column_index+" "+ path);
            cursor.close();
            return path;
        }
        System.out.println("after cursor "+cursor);
        return uri.getPath();
        }catch (Exception e){
            System.out.println("getPath "+ e.getMessage());
        }
        return null;
    }
}