package com.wei.wanandroid.javasources.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 创建线程的三种方式
 * @author: WEI
 * @date: 2018/3/26
 */

public class ThreadCreateMethods {


    public static void main(String[] args) {
        ThreadDemo mThreadDemo = new ThreadDemo();
        mThreadDemo.start();

        RunnableDemo runnableDemo = new RunnableDemo();
        Thread thread = new Thread(runnableDemo);
        thread.start();

        CallableDemo callableDemo = new CallableDemo();
        FutureTask futureTask = new FutureTask(callableDemo);
        Thread thread1 = new Thread(futureTask);
        thread1.start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

/**
 *  1.继承 Thread 类创建线程
 *  a.新建一个类继承 Thread 类，并重写 Thread 类的 run() 方法。
 *  b.创建 Thread 子类的实例。                  ThreadDemo mThreadDemo = new ThreadDemo();
 *  c.调用该子类实例的 start() 方法启动该线程。 mThreadDemo.start();
 */
class ThreadDemo extends Thread
{
    @Override
    public void run() {
//        super.run();
        System.out.println("继承Thread类来创建线程！");
    }
}

/**
 * 2.实现Runnable接口来创建线程
 * a.创建一个类实现 Runnable 接口，并重写该接口的 run() 方法。
 * b.创建该实现类的实例。                                          RunnableDemo runnableDemo = new RunnableDemo();
 * c.将该实例传入 Thread(Runnable r) 构造方法中创建 Thread 实例。  Thread thread = new Thread(runnableDemo);
 * d.调用该 Thread 线程对象的 start() 方法。                       thread.start();
 */
class RunnableDemo implements Runnable
{

    @Override
    public void run() {
        System.out.println("实现Runnable接口来创建线程");
    }
}

/**
 * 3.使用 Callable 和 FutureTask 创建线程
 * 创建一个类实现 Callable 接口，并重写 call() 方法。
 * 创建该 Callable 接口实现类的实例。
 * 将 Callable 的实现类实例传入 FutureTask(Callable callable) 构造方法中创建 FutureTask 实例。
 * 将 FutureTask 实例传入 Thread(Runnable r) 构造方法中创建 Thread 实例。
 * 调用该 Thread 线程对象的 start() 方法。
 * 调用 FutureTask 实例对象的 get() 方法获取返回值。
 */
 class CallableDemo implements Callable<String>
{

    @Override
    public String call() throws Exception {
        System.out.println("使用 Callable 和 FutureTask 创建线程");
        return "I'm Callable";
    }
}
