package com.example.readsmsandcalllog;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyClient {
    private RequestQueue requestQueue;
    private Context context;
    public static String URL = "https://reqres.in/api/users";

    public VolleyClient(Context context){
        this.context = context;
    }
    public RequestQueue getInstance(){
        requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }
}
