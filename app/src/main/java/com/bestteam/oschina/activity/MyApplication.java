package com.bestteam.oschina.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by ywf on 2017/2/17.
 */
public class MyApplication  extends Application {

    private static  Context context;

    public static Context getContext(){
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
}
