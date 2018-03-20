package com.wei.wanandroid.javasources.thread;

/**
 * 死锁的例子,DeadLockDemo一定要是继承Runnable的
 * @author: WEI
 * @date: 2018/3/20
 */

public class DeadLockDemo implements Runnable
{
    A a = new A();
    B b = new B();

    public static void main(String[] args) {
        DeadLockDemo deadLockDemo = new DeadLockDemo();
        new Thread(deadLockDemo).start();
        deadLockDemo.startMainThread();
    }

    private void startMainThread() {
        a.invokeA(b);
    }

    @Override
    public void run() {
        b.invokeB(a);
    }
}

class A
{
    public synchronized void invokeA(B b)
    {
        System.out.println( "当前线程 ： " + Thread.currentThread().getName() + " 调用到 A 的 invokeA 方法");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( "当前线程 ： " + Thread.currentThread().getName() + " 试图调用 B 的 next 方法");
        b.next();
    }

    public synchronized void next()
    {
        System.out.println( "当前线程 ： " + Thread.currentThread().getName() + " 调用到 A 的 next 方法");
    }
}

class B
{
    public synchronized void invokeB(A a)
    {
        System.out.println( "当前线程 ： " + Thread.currentThread().getName() + " 调用到 B 的 invokeB 方法" );
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( "当前线程 ： " + Thread.currentThread().getName() + " 试图调用 A 的 next 方法");
        a.next();
    }

    public synchronized void next()
    {
        System.out.println( "当前线程 ： " + Thread.currentThread().getName() + " 调用到 B 的 next 方法" );
    }
}
