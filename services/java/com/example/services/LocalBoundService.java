package com.example.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

public class LocalBoundService extends Service {
    private final IBinder binder = new LocalBinder();
    public LocalBoundService() {
    }

    public class LocalBinder extends Binder {
        LocalBoundService getService(){
            return LocalBoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");


        return binder;
    }

//    method 1
//    by calling method from ui thread

    int randomNum;
//    public int getRandomNumber(){
//        Random random = new Random();
//        randomNum =  random.nextInt(100);
//        return randomNum;
//    }

//    method - 2
//    by using callback method

    public interface RandomNumberCallback{
        void onRandomNumberGenerated(int randomNumber);
    }

//    public void getRandomNumberInBackground(final RandomNumberCallback callback){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Random random = new Random();
//                int i=0;
//                while (i<1000){
//                    int randomNumber = random.nextInt(100);
//                    callback.onRandomNumberGenerated(randomNumber);
//                }
//            }
//        }).start();
//    }

//    method - 3
//    by using handler method

    public void getRandomNumberInBackground(final Handler handler, TextView textView){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int i=0;
                while (i<100){
                    int randomNumber = random.nextInt(100);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(""+randomNumber);
                            Log.i("random number by handler", "run: handler method" + randomNumber);
                        }
                    });
                }
            }
        }).start();
    }
}