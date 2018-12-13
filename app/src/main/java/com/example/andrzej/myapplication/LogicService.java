package com.example.andrzej.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class LogicService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();



    public class LocalBinder extends Binder {
        LogicService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LogicService.this;
        }
    }

    public static double add(double n1, double n2){
        return n1 + n2;
    }
    public static double sub(double n1, double n2){
        return n1 - n2;
    }
    public static double mul(double n1, double n2){
        return n1 - n2;
    }
    public static double div(double n1, double n2){
        return n1 - n2;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}

