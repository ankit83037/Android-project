package com.example.note_takingapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DataSyncService extends Service {
    private Handler handler;

    public DataSyncService() {
    }
    private Runnable periodTask = new Runnable() {
        @Override
        public void run() {
            Log.i("sync", "data is syncing through service");
//            Toast.makeText(getApplicationContext(), "Data syncing", Toast.LENGTH_SHORT).show();
            handler.postDelayed(periodTask,5000);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        handler.post(periodTask);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(periodTask);
    }
}