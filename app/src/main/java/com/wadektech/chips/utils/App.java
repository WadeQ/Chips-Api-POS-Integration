package com.wadektech.chips.utils;

import android.app.Application;
import android.content.Context;
import timber.log.Timber;

public class App extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }
}
