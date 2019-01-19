package com.bawei.shopcardemo.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class LVApplication extends Application {
    private static LVApplication instance;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sharedPreferences = getSharedPreferences("application",
                Context.MODE_PRIVATE);
    }
    public static LVApplication getInstance() {
        return instance;
    }

    public SharedPreferences getShare() {
        return sharedPreferences;
    }
}
