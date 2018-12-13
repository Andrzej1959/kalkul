package com.example.andrzej.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        n1EditText = (EditText) findViewById(R.id.n1EditText);
        n2EditText = (EditText) findViewById(R.id.n2EditText);
        resEditText = (EditText) findViewById(R.id.resEditText);


    }

    public void piButtonClick(View v) {
        progressBar.setProgress(0);
        new PiComputeTask().execute();


      //  n1EditText.setText();
    }

    EditText n1EditText;
    EditText n2EditText;
    EditText resEditText;
    ProgressBar progressBar;

    public void addButtonClick(View v) {
        ((Button)v).setText("lplplp");
        ((Button)v).getText();
        if (mBound){
            //double final x = Double.valueOf(n1EditText.getText())
            Double res = LogicService.add(Double.valueOf(n1EditText.getText().toString()), Double.valueOf(n2EditText.getText().toString())) ;
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