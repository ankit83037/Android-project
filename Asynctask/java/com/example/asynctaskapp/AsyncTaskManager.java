package com.example.asynctaskapp;

import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Random;

public class AsyncTaskManager extends AsyncTask<Boolean,Void,Integer> {
    TextView textView;

    public AsyncTaskManager(TextView textView){
        this.textView = textView;
    }
    @Override
    protected Integer doInBackground(Boolean ...generateRandomNo) {
        int randomNumber = 0;
//        while(generateRandomNo[0]){
            Random random = new Random();
            randomNumber = random.nextInt(100);
//        }
        return randomNumber;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        textView.setText(""+result);
    }
}
