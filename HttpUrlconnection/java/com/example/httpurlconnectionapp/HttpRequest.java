package com.example.httpurlconnectionapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequest {
    public void sendHttpRequest(String reqUrl, Handler handler,String type){
        Thread httpRequestThread = new Thread(){
            public void run(){
                try {
                    
                StringBuilder stringBuilder = new StringBuilder();
                URL url = new URL(reqUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    Message message = Message.obtain(null,101);
                if(type.equals("text")){
                    String line = bufferedReader.readLine();
                    while (line != null){
                        stringBuilder.append(line);
                        line = bufferedReader.readLine();
                        Bundle bundle = new Bundle();
                        bundle.putString("response",stringBuilder.toString());
                        message.setData(bundle);
                    }
                }else if(type.equals("image")){
                    Log.i("inputstream of image ", "run: "+inputStream);
                    InputStreamReader inputStreamReader1 = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);
//                    System.out.println("image json "+ bufferedReader1.readLine());

                    String line;
                    while ((line=bufferedReader1.readLine())!=null){

                    }

                    Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(inputStream));
                    message.what = 102;
                    message.obj = bitmap;
                    Log.i("bitmap image", "run: httprequest image" + bitmap);
                }

                bufferedReader.close();
                connection.disconnect();
                handler.sendMessage(message);
                }catch (Exception e){
                    Log.i("not connected", "run: "+e.getMessage());
                }finally {

                }
            }
        };
        httpRequestThread.start();

    }

    public void postRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(1);
                arrayList.add(2);

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(1);
                jsonArray.put(2);
                try {
                    JSONObject postBody = new JSONObject();
                    postBody.put("name","Raju");
                    postBody.put("job","Manager");
                    postBody.put("list",arrayList);
                    postBody.put("num",1);
                    postBody.put("jsonArray",jsonArray);

                    URL url = new URL("https://reqres.in/api/users");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type","application/json");
                    connection.setDoOutput(true);

                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(1000);

                    OutputStream outputStream = connection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                    bufferedWriter.write(postBody.toString());
                    bufferedWriter.flush();

                    if(connection.getResponseCode()==201){

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line=bufferedReader.readLine())!=null){
                            Log.i("success", "run: post data added \n"+line);
                        }
                    }else{
                        Log.i("failure", "run: post data not added");
                    }


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
