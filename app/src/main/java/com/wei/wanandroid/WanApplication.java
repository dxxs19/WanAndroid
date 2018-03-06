package com.wei.wanandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.GcmRegister;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.wei.wanandroid.activity.MainActivity;

/**
 * @author: WEI
 * @date: 2017/10/31
 */

public class WanApplication extends Application
{
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

}
