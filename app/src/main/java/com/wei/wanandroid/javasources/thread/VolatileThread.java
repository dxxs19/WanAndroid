package com.wei.wanandroid.javasources.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: WEI
 * @date: 2018/5/23
 */
public class VolatileThread
{
    boolean run1 = false;
    boolean run2 = true;

    public static void main(String[] args) {
        new VolatileThread().testVolatile();
    }

    private void testVolatile() {
        ExecutorService fixedThreadPool = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.SECONDS, new SynchronousQueue<>(), new SpecialThreadFactory("fixed"));
        fixedThreadPool.submit(new Runnable1());
        fixedThreadPool.submit(new Runnable2());
        fixedThreadPool.shutdown();
    }

    class Runnable1 implements Runnable
    {
        @Override
        public void run() {
            for (int i = 0; i < 50; i ++)
            {
                if (i == 4)
                {
                    run2 = false;
                }
                System.out.println("Runnable1 i = " + i + ", run2 = " + run2 + ", run1 = " + run1);
            }
        }
    }

    class Runnable2 implements Runnable
    {
        @Override
        public void run() {
            for (int i = 0; i < 50; i ++)
            {
                if (i == 1)
                {
                    run1 = true;
                }
                System.out.println("Runnable2 i = " + i + ", run2 = " + run2 + ", run1 = " + run1);
            }
        }
    }


}
