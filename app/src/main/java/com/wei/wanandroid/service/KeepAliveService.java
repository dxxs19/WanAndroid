package com.wei.wanandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.wei.utillibrary.ToastUtils;

/**
 * @author Administrator
 */
public class KeepAliveService extends Service
{
    private final String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "--- onCreate ---");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        ToastUtils.showToast(this, "服务启动", Toast.LENGTH_SHORT);
        Log.e(TAG, "--- onStartCommand ---");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "--- onDestroy ---");
    }
}
