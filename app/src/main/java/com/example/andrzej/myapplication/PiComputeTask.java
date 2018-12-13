package com.example.andrzej.myapplication;

import android.os.AsyncTask;

public class PiComputeTask extends AsyncTask<Void, Integer, Double> {
    @Override
    protected Double doInBackground(Void... voids) {
        return 1111111.0;
    }

    protected void onPostExecute(Double result) {
        return;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        return;
    }


}
