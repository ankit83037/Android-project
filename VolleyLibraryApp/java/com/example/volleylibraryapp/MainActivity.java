package com.example.volleylibraryapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button loadData,postData;
    TextView showTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData = findViewById(R.id.LoadData);
        showTextView = findViewById(R.id.showTextView);
        postData = findViewById(R.id.PostData);

        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendHttpRequest();
            }
        });

        postData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postHttpRequest();
            }
        });

    }

    private void sendHttpRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://riad-news-api.vercel.app/api/news";
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                StringBuilder stringBuilder = new StringBuilder();

                try {
                    
                JSONArray responseJSONArray = response.getJSONArray("data");
                for(int i=1;i<10;i++){
                    stringBuilder.append("\n"+"Title\n"+responseJSONArray.getJSONObject(i).get("title")+"\n"+
                            "Source\n"+responseJSONArray.getJSONObject(i).get("source")+"\n"+
                            "Description\n"+responseJSONArray.getJSONObject(i).get("description")+"\n");
                }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
                }
                showTextView.setText(stringBuilder.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Error while making request", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(objectRequest);
    }

    public void postHttpRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://reqres.in/api/users";

//        to post data if it is json we can simply use jsonobject
//                if it is string then we have to create getParams or getBody

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(1);
        jsonArray.put("2");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name","Raju");
            jsonObject.put("job","manager");
            jsonObject.put("jsonArray",jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.i("response", "onResponse: "+jsonObject);
                    JSONArray jsonArray1 = jsonObject.getJSONArray("jsonArray");
                    Log.i("jsonArray response", "onResponse: "+jsonArray1);

                    String name = jsonObject.getString("name");
                    Toast.makeText(MainActivity.this, "post method successfull "+name, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("error in post method", "onErrorResponse: "+volleyError.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}