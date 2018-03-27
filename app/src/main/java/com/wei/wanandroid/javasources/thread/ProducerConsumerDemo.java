package com.wei.wanandroid.javasources.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生产者消费者问题
 * @author: WEI
 * @date: 2018/3/27
 */

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        Resource resource = new Resource();
        Producer producer = new Producer(resource);
        Consumer consumer = new Consumer(resource);
        Producer producer1 = new Producer(resource);
        Consumer consumer1 = new Consumer(resource);
        Producer producer2 = new Producer(resource);
        Consumer consumer2 = new Consumer(resource);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(producer);
        executorService.submit(consumer);
        executorService.submit(producer1);
        executorService.submit(consumer1);
        executorService.submit(producer2);
        executorService.submit(consumer2);
    }
}

/**
 * 生产者
 */
class Producer implements Runnable
{
    Resource mResource;

    public Producer(Resource resource) {
        mResource = resource;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mResource.increase();
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable
{
    Resource mResource ;

    public Consumer(Resource resource) {
        mResource = resource;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mResource.decrease();
        }
    }
}

/**
 * 资源类，供生产者及消费者操作
 */
class Resource
{
    /**
     * 当前数量
     */
    private int currentNum = 0;

    /**
     * 最大容量，如果生产者生产的资源数量大于maxSize，则要暂停生产，等待消费者消费后，收到通知再继续生产
     */
    private int maxSize = 20;

    /**
     * 生产资源
     */
    public synchronized void increase()
    {
        while ( currentNum >= maxSize )
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentNum ++;
        System.out.println("生产了1个资源，目前有 " + currentNum + " 个！");
        notifyAll();
    }

    /**
     * 消费资源
     */
    public synchronized void decrease()
    {
        while ( currentNum <= 0 )
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentNum --;
        System.out.println("消费了1个资源，目前还剩下 " + currentNum + " 个！");
        notifyAll();
    }
}