package com.example.httpurlconnectionbase64encodedurl;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncTaskManager extends AsyncTask<Void,Boolean,Boolean> {

    private Context context;
    public AsyncTaskManager(Context context){
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpUrlConnectionManager httpUrlConnectionManager = new HttpUrlConnectionManager();
        boolean isSuccessful = httpUrlConnectionManager.postData();

        return isSuccessful;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(result){
            Toast.makeText(context, "Data posted successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }

    }
}
