package com.example.httpurlconnectionbase64encodedurl;

import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class HttpUrlConnectionManager {
    private HttpURLConnection httpURLConnection;

    public HttpUrlConnectionManager(){
        httpURLConnection = new HttpClient().getClientInstance();
    }

    public boolean postData(){
        boolean isSuccessful = false;
        try{

            JSONObject postBody = new JSONObject();
            postBody.put("name","Ankit");
            postBody.put("job","Intern");
            postBody.put("id",1);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(postBody.toString());
            bufferedWriter.flush();

            if(httpURLConnection.getResponseCode() ==201){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    Log.i("success", "run: post data added \n"+line);
                    isSuccessful = true;
                }
            }else{
                Log.i("failure", "run: post data not added");
            }
        }catch (Exception e){
            System.out.println("httpurl " + e.getMessage());
        }
        return isSuccessful;
    }


}
