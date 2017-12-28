package com.wei.wanandroid.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * @author Administrator
 */
public class AutoService extends AccessibilityService
{
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final String tempClass = event.getClassName().toString();
        Log.e(TAG, "tempClass : " + tempClass);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "服务已连接！");
        startService(new Intent(this, KeepAliveService.class));
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "--- onInterrupt ---");
        Log.e(TAG, "服务已中断！");
    }

}
