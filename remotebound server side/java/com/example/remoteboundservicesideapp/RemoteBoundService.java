package com.example.remoteboundservicesideapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.Random;

public class RemoteBoundService extends Service {
    boolean isGenerate;
    int randomNumber;
    public static final int FLAG_MESSAGE = 0;
    public RemoteBoundService() {
    }
    private class RandomNumberRequestHandler extends Handler{
        public void handleMessage(Message msg){
            switch (msg.what){
                case FLAG_MESSAGE:
                    Message message = Message.obtain(null,FLAG_MESSAGE);
                    message.arg1 = getRandomNumber();
                    try {
                        msg.replyTo.send(message);
                    }catch (RemoteException e){
                        Log.i("remote exception", "handleMessage: "+e);
                    }
            }
            super.handleMessage(msg);
        }
    }
    private Messenger randomNumberRequestMessanger = new Messenger(new RandomNumberRequestHandler());

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return randomNumberRequestMessanger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isGenerate = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startGenerateRandomNumber();
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopGenerateRandomNumber();
        Log.i("service is destroyed", "onDestroy: service destroy");
    }

    private void startGenerateRandomNumber() {
        Random random = new Random();
        while (isGenerate){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            randomNumber = random.nextInt(100);
            Log.i("service is running", "startGenerateRandomNumber: "+randomNumber);
        }
    }

    private void stopGenerateRandomNumber(){
        isGenerate = false;
        System.out.println("service stopped");
    }

    private int getRandomNumber(){
        return randomNumber;
    }
}