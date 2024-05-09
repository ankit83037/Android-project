package com.example.note_takingapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class SyncServiceSchedular {

    public static void scheduleSyncService(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SyncService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,PendingIntent.FLAG_MUTABLE);

        long intervalMillis = 1*1000;
        long triggerAtMillis = System.currentTimeMillis() + intervalMillis;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,triggerAtMillis,intervalMillis,pendingIntent);
    }

    public static void cancelSyncService(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context,SyncService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,0);
        alarmManager.cancel(pendingIntent);
    }
}
