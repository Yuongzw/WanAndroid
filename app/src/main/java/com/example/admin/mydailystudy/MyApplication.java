package com.example.admin.mydailystudy;

import android.app.Application;
import android.content.Context;

import com.zhy.changeskin.SkinManager;

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SkinManager.getInstance().init(mContext);
    }

    public static Context getContext() {
        return mContext;
    }
}
