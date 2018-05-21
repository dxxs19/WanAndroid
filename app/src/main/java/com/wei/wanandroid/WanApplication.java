package com.wei.wanandroid;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.os.StrictMode;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author: WEI
 * @date: 2017/10/31
 */

public class WanApplication extends Application
{
    private final String TAG = getClass().getSimpleName();
    private RefWatcher refWatcher;
    private static WanApplication mApplication;

    public interface MsgDisplayListener {
        void handle(String msg);
    }

    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mApplication = this;
        Debug.startMethodTracing("trace_test");
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "Application onCreate!!!");
        super.onCreate();
        Fresco.initialize(this);
        setupLeakCanary();
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
        enabledStrictMode();
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

    public static WanApplication getAppContext()
    {
        return mApplication;
    }
}
