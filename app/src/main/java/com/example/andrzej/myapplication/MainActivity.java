package com.example.andrzej.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        n1EditText = (EditText) findViewById(R.id.n1EditText);
        n2EditText = (EditText) findViewById(R.id.n2EditText);
        resEditText = (EditText) findViewById(R.id.resEditText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public class PiComputeTask extends AsyncTask<Void, Integer, Double> {
        @Override
        protected Double doInBackground(Void... voids) {
            Random generator = new Random();
            double wKole = 0;

            for(int k=0; k<100; k++) {
                for (int i = 0; i < 100000; i++) {
                        double x, y;
                        x = generator.nextDouble();
                        y = generator.nextDouble();
                        if(x*x + y*y <1.0) wKole++;
                }
                publishProgress(k);
            }
            return (double)wKole * 0.0000004;
        }

        protected void onPostExecute(Double result) {
                n1EditText.setText(result.toString());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
    }

    public void piButtonClick(View v) {
        progressBar.setProgress(0);
        new PiComputeTask().execute();
    }

    EditText n1EditText;
    EditText n2EditText;
    EditText resEditText;
    ProgressBar progressBar;

    public void addButtonClick(View v) {
        if (mBound){
            Double res = LogicService.add(Double.valueOf(n1EditText.getText().toString()), Double.valueOf(n2EditText.getText().toString())) ;
            resEditText.setText(res.toString());
        }
    }

    public void mulButtonClick(View v) {
        if (mBound){
            Double res = LogicService.mul(Double.valueOf(n1EditText.getText().toString()), Double.valueOf(n2EditText.getText().toString())) ;
            resEditText.setText(res.toString());
        }
    }
    public void subButtonClick(View v) {
        if (mBound){
            Double res = LogicService.sub(Double.valueOf(n1EditText.getText().toString()), Double.valueOf(n2EditText.getText().toString())) ;
            resEditText.setText(res.toString());
        }
    }

    public void divButtonClick(View v) {
        if (mBound){
            Double res = LogicService.div(Double.valueOf(n1EditText.getText().toString()), Double.valueOf(n2EditText.getText().toString())) ;
            resEditText.setText(res.toString());
        }
    }

    LogicService logicService;
    boolean mBound = false;

    private ServiceConnection logicConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            LogicService.LocalBinder binder = (LogicService.LocalBinder) service;
            logicService = binder.getService();
            mBound = true;
            Toast.makeText(MainActivity.this, "Logic Service Connected!",
                    Toast.LENGTH_SHORT).show();     }


        public void onServiceDisconnected(ComponentName className) {
            logicService = null;
            mBound = false;
            Toast.makeText(MainActivity.this, "Logic Service Disconnected!",
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override protected void onStart() {
        super.onStart();

        if (!mBound) {
            this.bindService(new Intent(MainActivity.this,LogicService.class),
                logicConnection,Context.BIND_AUTO_CREATE);
        }
    }


    @Override protected void onStop() {
        super.onStop();
        if (mBound) {
            mBound = false;
            this.unbindService(logicConnection);
        }
    }
}