package com.wei.wanandroid.javasources.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: WEI
 * @date: 2018/3/27
 */

public class ThreadLockDemo
{
    volatile int count = 0;

    public static void main(String[] args) {
        new ThreadLockDemo().testThreads();
    }

    private void testThreads() {
        new Thread(new Runnable1()).start();
        new Thread(new Runnable2()).start();
    }

    class Runnable1 implements Runnable
    {
        @Override
        public void run() {
            while (count < 40)
            {
                System.out.println(Thread.currentThread().getName() + " before count = " + count);
                count++;
                System.out.println(Thread.currentThread().getName() + " after count = " + count);
            }
        }
    }

    class Runnable2 implements Runnable
    {
        @Override
        public void run() {
            while (count > -10) {
                System.out.println(Thread.currentThread().getName() + " before count = " + count);
                count--;
                System.out.println(Thread.currentThread().getName() + " after count = " + count);
            }
        }
    }
}
