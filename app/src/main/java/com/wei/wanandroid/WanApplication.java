package com.wei.wanandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;
import com.wei.utillibrary.OSUtil;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

/**
 * @author: WEI
 * @date: 2017/10/31
 */

public class WanApplication extends Application //implements HasActivityInjector
{
    private final String TAG = getClass().getSimpleName();
    private RefWatcher refWatcher;
    private static WanApplication mApplication;

    public interface MsgDisplayListener {
        void handle(String msg);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mApplication = this;
//        Debug.startMethodTracing("trace_test");
        MultiDex.install(this);
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "Application onCreate!!!");
        super.onCreate();
        Fresco.initialize(this);
        setupLeakCanary();
        // 小米5：256M；设置 android:largeHeap="true" 后 变成：512M
        Log.e(TAG, "App 可分配内存大小为 ：" + OSUtil.getMaxMemory() + "M");
        initX5();
//        DaggerMyAppComponent.create().inject(this);
    }


    private void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            // 这个过程专门用于堆分析，不应该在此初始化你的app。如果堆分析的进程正在运行，则什么也不做，直接return。
            // 否则执行后面的install（）来启动进程。这样我们就可以使用LeakCanary了，如果检测到某个Activity 有内存泄露，LeakCanary 就会给出提示。
            refWatcher = RefWatcher.DISABLED;
            return;
        }
//        enabledStrictMode();
        refWatcher = LeakCanary.install(this);
    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    /**
     * 其它地方可通过此方法获取到refWaatcher
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context)
    {
        return ((WanApplication)context.getApplicationContext()).refWatcher;
    }

    /**
     * 初始化腾讯浏览器X5内核
     */
    private void initX5() {
        QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + b);
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), callback);

    }

    public static WanApplication getAppContext()
    {
        return mApplication;
    }
}
