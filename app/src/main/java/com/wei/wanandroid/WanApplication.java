package com.wei.wanandroid;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author: WEI
 * @date: 2017/10/31
 */

public class WanApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
