package com.wei.wanandroid.activity.memoryopt;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

import java.lang.ref.SoftReference;

/**
 * LeakCanary框架研究
 * @author WEI
 */
public class LeakCanaryActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);
        LeakThread leakThread = new LeakThread(this);
        leakThread.start();
//        MyHandler myHandler = new MyHandler(this);
//        Message message = myHandler.obtainMessage();
//        message.obj = "I love beauty!";
//        myHandler.sendMessageDelayed(message, 5*1000);
    }

    static class MyHandler extends Handler
    {
        SoftReference<LeakCanaryActivity> softReference;

        public MyHandler(LeakCanaryActivity leakCanaryActivity) {
            softReference = new SoftReference<>(leakCanaryActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(LeakCanaryActivity.class.getSimpleName(), "--- handleMessage ---" + msg.obj);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Context context = softReference.get();
            Log.e(LeakCanaryActivity.class.getSimpleName(), "context = " + context);
        }
    }

    @Override
    public void initView() {

    }

    class LeakThread extends Thread {
        Context context;
        public LeakThread(LeakCanaryActivity leakCanaryActivity) {
            context = leakCanaryActivity;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(6 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
