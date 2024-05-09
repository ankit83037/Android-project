package com.example.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StartedServices extends Service {
    public StartedServices() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("started services", "onStartCommand: service is running");
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return null;
    }
}