package com.example.note_takingapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SyncService extends Service {
    public SyncService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("sync service", "onStartCommand: SynService" );
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
}