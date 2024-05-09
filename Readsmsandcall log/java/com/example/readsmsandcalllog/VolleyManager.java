package com.example.readsmsandcalllog;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VolleyManager {

    public void uploadCallLog(Context context, JSONArray callLogjsonArray){

        RequestQueue requestQueue = new VolleyClient(context).getInstance();
        JSONObject callLogDetail = new JSONObject();
        try {
            callLogDetail.put("callLogDetail",callLogjsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, VolleyClient.URL, callLogDetail, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                JSONArray jsonArray1 = null;
                try {
                    jsonArray1 = jsonObject.getJSONArray("callLogDetail");
                } catch (JSONException e) {
                    System.out.println("call log detail "+ e.getMessage());
                }
                Log.i("jsonArray response", "onResponse: "+jsonArray1);
                Toast.makeText(context, "call log uploaded successfully", Toast.LENGTH_SHORT).show();
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
