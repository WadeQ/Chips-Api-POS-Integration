package com.wadektech.chips;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class App extends Application {
    public static App instance ;

    public App(){
        instance = this;
    }
    public static Context getContext(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

}
