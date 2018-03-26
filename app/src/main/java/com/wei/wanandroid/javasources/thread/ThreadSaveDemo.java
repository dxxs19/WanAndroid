package com.wei.wanandroid.javasources.thread;

/**
 * 线程安全例子
 * @author: WEI
 * @date: 2018/3/26
 */

public class ThreadSaveDemo implements Runnable
{
    int ticketCount = 100;

    @Override
    public void run()
    {
        while ( true )
        {
            synchronized (this)
            {
                if (ticketCount <= 0)
                {
                    break;
                }
                ticketCount --;
                if (ticketCount % 25 == 0)
                {
                    Thread.yield();
                }
                System.out.println(Thread.currentThread().getName() + " 卖出第 " + ( 100 - ticketCount) + " 张电影票，剩余票数：" + ticketCount);
            }
        }
    }

    public static void main(String[] args) {
        ThreadSaveDemo threadSaveDemo = new ThreadSaveDemo();

        Thread thread1 = new Thread(threadSaveDemo, "窗口 1");
        Thread thread2 = new Thread(threadSaveDemo, "窗口 2");
        thread1.start();
        thread2.start();
    }
}


