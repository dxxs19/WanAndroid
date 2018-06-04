package com.wei.wanandroid.javasources.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author: WEI
 * @date: 2017/12/1
 */

public class ClassLoaderTest
{
    public static void main(String[] args)
    {
        System.out.println(System.getProperty("sun.boot.class.path"));
        // 获取系统类加载器AppClassLoader
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统类加载器：" + systemLoader);
        try {
            Enumeration<URL> enumeration = systemLoader.getResources("");
            while (enumeration.hasMoreElements())
            {
                System.out.println(enumeration.nextElement());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取系统类加载器的父类加载器——应该得到扩展类加载器ExtClassLoader
        ClassLoader extensionLoader = systemLoader.getParent();
        System.out.println("扩展类加载器：" + extensionLoader);
        System.out.println("扩展类加载器的加载路径：" + System.getProperty("java.ext.dirs"));
        // 返回null。这是因为根类加载器并没有继承ClassLoader抽象类，所以扩展类加载器getParent()返回null,
        // 但实际上，根类加载器确实是扩展类加载器的父类加载器。
        System.out.println("扩展类加载器的parent：" + extensionLoader.getParent());

        // sun.misc.Launcher$AppClassLoader@63947c6b，系统类加载器
        System.out.println(new ClassLoaderTest().getClass().getClassLoader());

        System.out.println( Runtime.getRuntime().availableProcessors() );
    }
}
