package com.muni.resistencia.Utils;

import android.app.Application;
import android.content.Context;

public class VecinosApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        VecinosApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return VecinosApplication.context;
    }
}
