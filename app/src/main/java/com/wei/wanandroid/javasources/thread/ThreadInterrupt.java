package com.wei.wanandroid.javasources.thread;

/**
 * 线程中断：当一个线程运行时，另一个线程可以调用对应的Thread对象的interrupt（）方法来中断它，该方法只是在目标线程中设置一个标志，
 *           表示它已经被中断，并立即返回。这里需要注意的是，如果只是单纯的调用interrupt（）方法，线程并没有实际被中断，会继续往下执行。
 * 待决中断：线程中断中，sleep（）方法的实现检查到休眠线程被中断，它会相当友好地终止线程，并抛出InterruptedException异常。另外一种情况，
 *           如果线程在调用sleep（）方法前被中断，那么该中断称为待决中断，它会在刚调用sleep（）方法时，立即抛出InterruptedException异常。
 * @author: WEI
 * @date: 2018/3/28
 */

public class ThreadInterrupt implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " is Running. Try to sleep for 2 second");
            Thread.sleep(2000);
            System.out.println("Wake up !");
        } catch (InterruptedException e) {
            System.out.println(" run - interruptedException ");
            e.printStackTrace();
            // 处理完中断异常后，返回到run()方法入口
            // 如果没有return,线程不会实际被中断，它会继续打印下面的信息
            return;
        }
        System.out.println("int run end!");
    }

    public static void main(String[] args)
    {
//        System.out.println(Thread.currentThread().getName() + " is Running !");
//        ThreadInterrupt threadInterrupt = new ThreadInterrupt();
//        Thread thread = new Thread(threadInterrupt);
//        thread.start();
//        //住线程休眠1秒，从而确保刚才启动的线程有机会执行一段时间
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(Thread.currentThread().getName() + " is Running ! Try to interrupt thread !");
//        // 中断线程时，线程正好处于休眠状态，则报InterruptedException: sleep interrupted异常
//        thread.interrupt();
//        System.out.println(" main end");

        Thread currentThread = Thread.currentThread();
        // 使用isInterrupted（）方法判断中断状态
        System.out.println("Before interrupt() : currentThread.isInterrupted() = " + currentThread.isInterrupted());
        // 中断自己
        currentThread.interrupt();
        System.out.println("After interrupt() : currentThread.isInterrupted() = " + currentThread.isInterrupted());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 报异常后，会清除中断标志，这里会返回false
        System.out.println("After Exception : currentThread.isInterrupted() = " + currentThread.isInterrupted());
    }
}
