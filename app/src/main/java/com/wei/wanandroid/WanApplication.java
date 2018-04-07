package com.wei.wanandroid;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

//import com.didi.virtualapk.PluginManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * @author: WEI
 * @date: 2017/10/31
 */

public class WanApplication extends Application
{
    private final String TAG = getClass().getSimpleName();
    private RefWatcher refWatcher;

    public interface MsgDisplayListener {
        void handle(String msg);
    }

    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();
//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        PluginManager.getInstance(base).init();
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        initHotfix();
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

    private void initHotfix() {
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }

        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                //.setAesKey("0123456789123456")
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            // SophixManager.getInstance().cleanPatches();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                        Log.e(TAG, "code = " + code);
                    }
                }).initialize();

//        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
