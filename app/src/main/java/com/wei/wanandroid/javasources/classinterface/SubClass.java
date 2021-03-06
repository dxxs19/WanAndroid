package com.wei.wanandroid.javasources.classinterface;

import java.lang.reflect.Method;

/**
 * 子类无条件的继承父类的无参构造方法；
 * 子类可以引用父类中的有参构造方法，使用super关键字；如果没有super关键字，则会先引用父类中的无参构造方法，再调用自己的有参构造方法
 * 如果子类没有构造方法，则父类无参构造方法作为自己的构造方法；
 * 如果子类有无参构造方法，父类的无参构造方法会先被调用，而不是被覆盖；
 * @author: WEI
 * @date: 2017/11/30
 */

public class SubClass extends SuperClass
{
    protected int a = 25;

    public static void main(String[] args) {
        SubClass cls1 = new SubClass();
        System.out.println("---------------------------");
        SubClass cls2 = new SubClass("");
        System.out.println("---------------------------");
        cls1.finalMethodTest();

        // 父类引用子类时，属性还是父类的属性，没有被子类改变；只有当子类引用子类时，属性才是子类的属性
        SuperClass superClass = new SubClass();
        System.out.println(superClass.a);

        // 父类的静态方法可以被继承，但不能被重写
        SubClass.testStaticMethod();
    }

    /**
     * 1.子类无条件的继承父类的无参构造方法。子类初始化时，会先调用父类的无参构造方法，再
     *    调用自己的构造方法.
     * 2.
      */
    public SubClass()
    {
        System.out.println("子类无参构造方法初始化！");
    }

    /**
     * 1. 如果没有这行代码：super(param);则初始化时会先调用父类的无参构造方法，再调用自己的有参构造方法;
     * 2. 如果有这行代码：super(param);则初始化时会先调用父类的有参构造方法，再调用自己的有参构造方法。
     * @param param
     */
    public SubClass(String param)
    {
        super(param);
        System.out.println("子类有参构造方法初始化！");
    }

    public void finalMethodTest()
    {
        // final 方法不能被子类的方法覆盖，但可以被继承
        finalMethod();
    }

//    protected static void testStaticMethod()
//    {
//        System.out.println("子类的静态方法被调用！");
//    }
}
