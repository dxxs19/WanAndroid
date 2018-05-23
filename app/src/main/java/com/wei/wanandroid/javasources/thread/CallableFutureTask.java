package com.wei.wanandroid.javasources.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: WEI
 * @date: 2018/5/22
 */
public class CallableFutureTask
{
    public static void main(String[] args)
    {
        // 创建Callable对象
        CallableThread callable = new CallableThread();
        // 使用 FutureTask 来包装 Callable 对象
        FutureTask futureTask = new FutureTask(callable)
        {
            @Override
            protected void done() {
                super.done();
                try {
                    // callable 一执行完，马上就执行下面一行代码
                    System.out.println("futureTask 的 done 方法获取到的结果 : " + get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(futureTask, "有返回值的线程");
        for (int i = 0; i < 10000; i ++)
        {
            System.out.println(Thread.currentThread().getName() + " 的循环变量 i = " + i);
            if (i == 0)
            {
                // 实质还是以Callable对象来创建、并启动线程
                thread.start();
            }
        }

        try {
            // 获取线程返回值
            System.out.println("子线程的返回值 : " + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class CallableThread implements Callable<Integer>
    {
        // 线程执行体
        @Override
        public Integer call() throws Exception
        {
            int i = 0;
            for (; i < 1000; i ++)
            {
                System.out.println(Thread.currentThread().getName() + " 的循环变量 i = " + i);
            }
            // call()可以有返回值
            return i;
        }
    }
}
