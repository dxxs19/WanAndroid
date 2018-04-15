package com.wei.wanandroid.javasources.designpatterns.singleton;

/**
 * 枚举创建单例.
 * 写法简单,枚举类在Java中与普通的类是一样的,不仅能够有字段,还能够有自己的方法.最重要的是默认枚举实例的
 * 创建是线程安全的，并且在任何情况下它都是一个单例（包括反序列化）。
 */
public enum  EnumSingleton {
    INSTANCE;

    public void doSomething()
    {
        System.out.println( "do something !");
    }
}
