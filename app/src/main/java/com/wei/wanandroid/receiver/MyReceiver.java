package com.wei.wanandroid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wei.wanandroid.service.KeepAliveService;

/**
 * @author Administrator
 */
public class MyReceiver extends BroadcastReceiver
{
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        Log.e(TAG, "action = " + action);
        context.startService(new Intent(context, KeepAliveService.class));
    }
}
