package com.wei.wanandroid.activity.memoryopt;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
    }

    @Override
    public void initView() {

    }

    class LeakThread extends Thread {
        LeakCanaryActivity context;
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
