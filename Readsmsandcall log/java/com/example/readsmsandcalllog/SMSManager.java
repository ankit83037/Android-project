package com.example.readsmsandcalllog;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsMessage;

import java.util.ArrayList;
import java.util.List;

public class SMSManager {

    public static ArrayList<SMSMessage> getSmsMessages(Context context){
        ArrayList<SMSMessage> smsMessageArrayList = new ArrayList<>();
        Uri smsUri = Uri.parse("content://sms/");

        Cursor cursor = context.getContentResolver().query(smsUri,null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                String sender = cursor.getString(cursor.getColumnIndex("address"));
                String body = cursor.getString(cursor.getColumnIndex("body"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                SMSMessage smsMessage = new SMSMessage(sender,body,date);
                smsMessageArrayList.add(smsMessage);
            }
            cursor.close();
        }

        return smsMessageArrayList;
    }
}
