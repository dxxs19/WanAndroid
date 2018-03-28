package com.wei.wanandroid.javasources.thread;

/**
 * 控制线程的方法(实现线程调度)
 * sleep, setPriority, yield, join, setDaemon
 * yield和join方法的使用
 * join方法用线程对象调用，如果在一个线程A中调用另一个线程B的join方法，线程A将会等待线程B执行完毕后再执行。
 * yield可以直接用Thread类调用，yield让出CPU执行权给同等级或者更高级别的线程，如果没有相应级别的线程在等待CPU的执行权，则该线程继续执行。
 * @author: WEI
 * @date: 2018/3/26
 */

public class ThreadControllerMethods{
    public static void main(String[] args) {
//        ThreadControllerMethods threadControllerMethods = new ThreadControllerMethods();
//        threadControllerMethods.testSleep();

        JoinTest joinTest = new JoinTest();
        for (int i = 0; i < 100; i ++)
        {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if ( i == 20)
            {
                joinTest.start();
                try {
                    // 子线程执行完后，主线程才继续执行
                    joinTest.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        DaemonDemo daemonDemo = new DaemonDemo();
        daemonDemo.setDaemon(true);
        daemonDemo.start();
        for (int i = 0; i < 30; i ++)
        {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }

    /**
     * 该方法的意思就是让正在运行状态的线程到阻塞状态，而这个时间就是线程处于阻塞状态的时间。
     */
    private void testSleep() {
        for (int i = 0; i < 10; i ++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println( Thread.currentThread().getName() + " thread sleep 1 second!");
        }
    }

    /**
     * 这个方法其实要有两个线程，也就是一个线程的线程执行体中有另一个线程在调用 join() 方法。举个例子，
     * Thread1 的 run() 方法执行体中有 Thread2 在调用 join()，这时候 Thread1 就会被阻塞，必须要等到 Thread2 的线程执行完成或者
     * join() 方法的时间到后才会继续执行。
     */
    static class JoinTest extends Thread
    {
        @Override
        public void run() {
            for (int i = 0; i < 100; i ++)
            {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
        }
    }

    /**
     * 后台线程
     * 这个方法就是将线程设置为后台线程，后台线程的特点就是当前台线程全部执行结束后，后台线程就会随之结束。
     * 此方法设置为 true 时，就是将线程设置为后台线程。 而 isDaemon() 就是返回此线程是否为后台线程。
     */
    static class DaemonDemo extends Thread
    {
        @Override
        public void run() {
            for (int i = 0; i < 100; i ++)
            {
                System.out.println(getName() + " " + isDaemon() + " " + i);
            }
        }
    }

}
