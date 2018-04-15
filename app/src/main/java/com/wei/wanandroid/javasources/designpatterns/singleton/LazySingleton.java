package com.wei.wanandroid.javasources.designpatterns.singleton;

/**
 * 懒汉式是典型的时间换空间，也就是每次获取实例都会进行判断，看是否需要创建实例，浪费判断的时间。当然，如果一直没有人使用的话，那就不会创建实例，则节约内存空间。
 * 懒汉单例模式：比较懒，在类加载时，不创建实例，因此类加载速度快。但第一次获取实例时，由于要实例化，所以速度慢。
 * 优点：单例只有在使用时才会被实例化，在一定程度上节约了资源；
 * 缺点：第一次加载时需要及时进行实例化，反应稍慢，最大的问题是每次调用getInstance 都进行同步，造成不必要的同步开销。
 * 这种模式一般不建议使用。
 */
public class LazySingleton {
    private static LazySingleton lazySingleton;

    private LazySingleton()
    {
    }

    /**
     * 一定要加 synchronized 关键字，否则无法保证在多线程情况下实例的唯一性。
     * synchronized 关键字能保证在多线程情况下单例对象的唯一性。但每次调用
     * getInstance 方法都会进行同步，这样会消耗不必要的资源，这也是懒汉单例模式存在的最大问题
     * @return
     */
    public static synchronized LazySingleton getInstance()
    {
        if (lazySingleton == null)
        {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
