package com.mason.brent.toolbar;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;

/**
 * Created by brent on 2016/8/8,008.
 */
public class TestApp extends Application{
    @Override
    public void onCreate() {
        NoHttp.initialize(this);
        super.onCreate();
    }
}
