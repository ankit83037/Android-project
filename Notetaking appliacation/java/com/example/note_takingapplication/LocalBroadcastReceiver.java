package com.example.note_takingapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LocalBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        
        if (intent.getAction().equals("com.demo.localBroadcast")){
            Toast.makeText(context, "record insert message from local broadcast receiver", Toast.LENGTH_LONG).show();
        }
    }
}