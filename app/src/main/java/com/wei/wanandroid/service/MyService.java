package com.wei.wanandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service
{
    private final static String TAG = MyService.class.getSimpleName();
    private int i = 0;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(mRunnable).start();
        return START_STICKY;
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true)
            {
                Log.e(TAG, "" + System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
