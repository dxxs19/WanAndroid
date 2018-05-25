package com.wei.wanandroid.javasources.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: WEI
 * @date: 2018/5/23
 */
public class SpecialThreadFactory implements ThreadFactory
{
    private String mNamePrefix;
    private static final AtomicInteger poolNumber = new AtomicInteger(1);

    public SpecialThreadFactory(String namePrefix) {
        mNamePrefix = namePrefix;
    }

    @Override
    public Thread newThread(@NonNull Runnable r)
    {
        return new Thread(r, mNamePrefix+ "-threadpool-" + poolNumber.getAndIncrement());
    }
}
