package com.example.readsmsandcalllog;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CallLogManager {

    public JSONArray getCallLog(Context context){
        JSONArray callLogJsonArray = new JSONArray();

        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,null,null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                String phoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String callType = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                JSONObject callLogJsonObject = new JSONObject();
                try {
                    callLogJsonObject.put("phoneNumber",phoneNumber);
                    callLogJsonObject.put("callType",callType);
                    callLogJsonObject.put("date",date);
                    callLogJsonObject.put("duration",duration);
                } catch (JSONException e) {
                    Log.i("call log json object", "getCallLog: "+e.getMessage());
                }
                callLogJsonArray.put(callLogJsonObject);
            }
            cursor.close();
        }
        return callLogJsonArray;
    }
}
