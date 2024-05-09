package com.example.asynctaskandhttpurlconnectionapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskManager extends AsyncTask<Void,Void,String> {

    TextView textView;

    public AsyncTaskManager(TextView textView){
        this.textView = textView;
    }
    @Override
    protected String doInBackground(Void... v) {

        String response = getHttpRequest();
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            textView.setText(""+jsonObject.getString("email")+"\n"+jsonObject.getString("first_name")+jsonObject.getString("last_name"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    private String getHttpRequest() {
        JSONObject jsonObject1 = null;
        try {
            URL url = new URL("https://reqres.in/api/users/2");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String line = bufferedReader.readLine();
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while (( line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            Log.i("succes httpurlconnection", "getHttpRequest: "+ stringBuilder.toString());
            bufferedReader.close();
            connection.disconnect();

//            JSONArray jsonArray = new JSONArray(stringBuilder);
//            int i=0;
//            while (i<jsonArray.length()){
//                JSONObject jsonObject = new JSONObject("data");
//            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            jsonObject1 = jsonObject.getJSONObject("data");


        }catch (Exception e){
            Log.i("error httpurlconnection", "getHttpRequest: not connected "+e.getMessage());
        }
        return jsonObject1.toString();
    }
}
