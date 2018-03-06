package com.wei.wanandroid.javasources.classinterface;

/**
 * final修饰的类不可以被继承
 * @author: WEI
 * @date: 2017/11/30
 */

public class SuperClass
{
    protected int a = 100;

    public SuperClass()
    {
        System.out.println("父类无参构造方法初始化！");
    }

    public SuperClass(String param)
    {
        System.out.println("父类有参构造方法初始化！");
    }

    /**
     * final 方法不能被子类的方法覆盖，但可以被继承
     */
    public final void finalMethod()
    {
        System.out.println("父类final方法");
    }
}
