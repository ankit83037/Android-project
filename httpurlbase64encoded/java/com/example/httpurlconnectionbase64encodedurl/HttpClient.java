package com.example.httpurlconnectionbase64encodedurl;

import android.util.Base64;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    private String url = "https://reqres.in/api/users";
    private String decodedUrl;
    private HttpURLConnection httpURLConnection;

    public HttpClient(){
        this.decodedUrl = getDecodedUrl();
    }
    public  HttpURLConnection getClientInstance(){

        System.out.println("decoded url "+ decodedUrl);
        try{
            URL url = new URL(decodedUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
        }catch (Exception e){
            System.out.println("error in http connection "+e.getMessage());
        }
        return httpURLConnection;

    }

    private String getEncodedUrl(){
        byte[] encodedBytes = url.getBytes();
        String encodedUrl = Base64.encodeToString(encodedBytes,Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
        return encodedUrl;
    }
    private String getDecodedUrl(){
        String base64EncodedUrl = getEncodedUrl();
        byte[] decodedBytes = android.util.Base64.decode(base64EncodedUrl, Base64.DEFAULT);
        return new String(decodedBytes);
    }
}
