package com.wei.wanandroid.javasources.designpatterns.singleton;

/**
 * 饿汉式是典型的空间换时间，当类装载的时候就会创建类实例，不管你用不用，先创建出来，然后每次调用的时候，就不需要再判断了，节省了运行时间。
 * 饿汉单例模式：比较饥渴，在类加载时就进行实例化，所以类加载较慢。但在获取实例时，由于已经实例化了，所以速度很快。
 * 优点：保证在多线程情况下单例对象的唯一性。写法也简单，使用时反应迅速；
 * 缺点：由于在类加载时就实例化，不管实例有没有用到。这样一来，有可能会浪费资源。
 */
public class HungrySingleton {
    private static final HungrySingleton HUNGRY_SINGLETON = new HungrySingleton();

    /**
     * 公有的静态函数，对外暴露获取单例对象的接口
     * @return
     */
    public static HungrySingleton getInstance()
    {
        return HUNGRY_SINGLETON;
    }

    /**
     * 构造函数私有，不允许其它类通过new来实例化本实例
     */
    private HungrySingleton()
    {

    }
}
