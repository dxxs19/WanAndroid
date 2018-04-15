package com.wei.wanandroid.javasources.designpatterns.singleton;

/**
 * Double Check Lock ：双重检查锁定
 * 优点：既能够在需要时才初始化单例，又能够保证线程安全，且单例对象初始化后调用getInstance不进行同步锁。
 * 缺点：第一次加载时反应稍慢。
 */
public class DCLSingleton {
    /**
     * volatile：保证在多线程情况下的可见性。即线程修改的值会立即被更新到主存，当有其他线程需要读取时，它会去内存中读取新值。
     */
    private volatile static DCLSingleton dclSingleton;
    private DCLSingleton()
    {
    }

    public static DCLSingleton getInstance()
    {
        if (dclSingleton == null)
        {
            synchronized (DCLSingleton.class)
            {
                if (dclSingleton == null)
                {
                    dclSingleton = new DCLSingleton();
                }
            }
        }
        return dclSingleton;
    }
}
