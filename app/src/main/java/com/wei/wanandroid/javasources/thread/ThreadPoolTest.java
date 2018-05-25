package com.wei.wanandroid.javasources.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: WEI
 * @date: 2018/2/26
 */
public class ThreadPoolTest
{
    public static void main(String[] args) {
        testScheduledThreadPool();
        testSingleThreadPool();
        testCacheThreadPool();
        testFixedThreadPool();
    }

    /**
     * 创建一个定时任务的线程池
     */
    private static void testScheduledThreadPool()
    {
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(4,
                new SpecialThreadFactory("scheduled"));
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟5秒后执行任务1");
            }
        }, 5, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("每隔12秒执行一次任务2");
            }
        }, 6, 12, TimeUnit.SECONDS);
    }

    /**
     * 创建只有一个线程的线程池，如果线程终止，
     * 他将会创建一个新的线程加入到池子中，这
     * 个线程池会保证池子中始终有一个线程
     */
    private static void testSingleThreadPool()
    {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new SpecialThreadFactory("single"));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("单线程池开始行动了！");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception
            {
                int i = 0;
                for ( ; i < 100; i ++)
                {
                    i += i;
                }
                return i;
            }
        };
//        Future future = executorService.submit(runnable);
        Future future = executorService.submit(callable);
        try {
            Object result = future.get();
            System.out.println("执行完了" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个可根据需要创建线程的线程池，但是
     * 当先前创建的线程可得到时就会重用先前的线
     * 程，如果不存在可得到的线程，一个新的线程
     * 将被创建并被加入到池子中。60秒没有被用到
     * 的线程将被终止并从缓存中移除
     */
    private static void testCacheThreadPool()
    {
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = new ThreadPoolExecutor(0, 10,
            60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new SpecialThreadFactory("cached"));
        distributeTaskForThreadPool(executorService);
    }

    /**
     * 创建一个带有固定线程的线程池
     */
    private static void testFixedThreadPool()
    {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
        ExecutorService executorService = new ThreadPoolExecutor(0, 4,
                0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5), new SpecialThreadFactory("fixed"));
        distributeTaskForThreadPool(executorService);
    }

    /**
     * 为线程池分配8个任务，使其驱动
     * @param threadPool
     */
    public static void distributeTaskForThreadPool(ExecutorService threadPool) {
        // 让线程池驱动8个任务
        for (int i = 1; i <= 8; i++) {
            // 由于内部类里面不能放一个非final的变量，所以我把i的值赋予task
            final int task = i;

            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("我是" + Thread.currentThread().getName()
                            + "，" + "拿到了第" + task + "个任务，我开始执行了");
                }
            });
        }
    }
}
