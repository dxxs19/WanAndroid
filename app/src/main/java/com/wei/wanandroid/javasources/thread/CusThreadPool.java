package com.wei.wanandroid.javasources.thread;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池。顺序执行的话，最大允许任务数为：最大线程数 + 队列容量。本例为 ： 5 + 10 = 15。
 * @author: WEI
 * @date: 2018/6/4
 */
public class CusThreadPool
{
    public static void main(String[] args)
    {
//        SynchronousQueue synchronousQueue = new SynchronousQueue();
        BlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 15,
                60, TimeUnit.MICROSECONDS, blockingDeque, new SpecialThreadFactory("cusThreadPool"),
                (r, executor) -> {
                    System.out.println("线程池已满，无法再接收任务了！！！");
//                        if (!executor.isShutdown()) {
//                            r.run();
//                        }
                });

        for (int i = 0; i < 25; i ++)
        {
            poolExecutor.execute(new CusTask(i));
        }
        poolExecutor.shutdown();
    }


    static class CusTask implements Runnable
    {
        private int mId;

        public CusTask(int id)
        {
            mId = id;
        }

        @Override
        public void run() {
            System.out.println("#" + mId + "  threadId = " + Thread.currentThread().getName() );
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}