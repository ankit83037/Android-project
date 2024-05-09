package com.example.socketprogrammingpostdataapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient {
    private String host = "https://reqres.in";
    private int port = 443;
    private String path = "api/users/";
    private String data;
    private  Socket socket;
    JSONObject jsonObject = new JSONObject();
    public void makePostRequest(){

        try {
            jsonObject.put("name","Shyam");
            jsonObject.put("job","manager");
            data = jsonObject.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                     socket = new Socket(host,80);
//                    InetSocketAddress endpoint = new InetSocketAddress(host,port);
//                    try {
//                        socket.connect(endpoint,15000);
//                    } catch (IOException e) {
//                        System.out.println("socket connection "+ e.getMessage());
//                    }

                    String postRequest = "POST "+path+"HTTP/1.1\r\n"+
                            "Host: " + socket.getInetAddress().getHostName()+"\r\n" +
                            "Content-Type: application/json\r\n" +
                            "Content-Length: " + data.length() + "\r\n" +
                            "Connection : close\r\n" +
                            "\r\n" +
                            data;

                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                    outputStreamWriter.write(postRequest);
                    outputStreamWriter.flush();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine())!=null){
                        Log.i("response", "makePostRequest: "+line);

                    }
                    }catch (Exception e){
                        System.out.println("error in socket connection "+e.getMessage());
                    }
                }
            }).start();


    }
}
