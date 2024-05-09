package com.example.note_takingapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String CUSTOM_INTENT = "com.demo.note";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        if(intent.getAction().equals("Intent.ACTION_BATTERY_CHANGED")){
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            Toast.makeText(context.getApplicationContext(), " "+level, Toast.LENGTH_SHORT).show();
        }
        else if (intent.getAction().equals(CUSTOM_INTENT)) {
            Toast.makeText(context, "insert message from broadcast receiver", Toast.LENGTH_SHORT).show();
        }

    }
}