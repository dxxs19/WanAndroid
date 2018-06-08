package com.wei.wanandroid.javasources.designpatterns.proxy;

import com.wei.wanandroid.javasources.designpatterns.proxy.dynamicproxy.DynamicProxy;
import com.wei.wanandroid.javasources.designpatterns.proxy.staticproxy.ILawsuit;
import com.wei.wanandroid.javasources.designpatterns.proxy.staticproxy.Lawyer;
import com.wei.wanandroid.javasources.designpatterns.proxy.staticproxy.XiaoMin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: WEI
 * @date: 2018/4/27
 */
public class TestProxy {
    public static void main(String[] args) {
        //    我们要代理的真实对象
        ILawsuit xiaoMin = new XiaoMin();
        ILawsuit lawer = new Lawyer(xiaoMin);
        lawer.submit();
        lawer.burden();
        lawer.defend();
        lawer.finish();
        System.out.println("=========================");
        // 构造一个动态代理; 我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        DynamicProxy dynamicProxy = new DynamicProxy(xiaoMin);
        // 获取被代理类小民的ClassLoader
        ClassLoader classLoader = xiaoMin.getClass().getClassLoader();
        // 动态构造一个代理者律师
        Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{ILawsuit.class}, dynamicProxy);
//        ILawsuit lawyer = (ILawsuit) Proxy.newProxyInstance(dynamicProxy.getClass().getClassLoader(), xiaoMin.getClass().getInterfaces(), dynamicProxy);
        ILawsuit lawyer = (ILawsuit) proxy;

        // 此处输出：com.sun.proxy.$Proxy0。也就是jvm动态生成的代理类
        String proxyClassName = proxy.getClass().getName();
        System.out.println( proxyClassName );
        new TestProxy().getProxyClassInfo(proxy.getClass());

        lawyer.submit();
        lawyer.burden();
        lawyer.defend();
        lawyer.finish();
    }

    private void getProxyClassInfo(Class<?> aClass)
    {
        Constructor<?>[] constructors = aClass.getDeclaredConstructors();
        for (Constructor constructor : constructors)
        {
            System.out.println(constructor);
        }
        Method[] methods = aClass.getMethods();
        for (Method method : methods)
        {
            System.out.println(method);
        }


        System.out.println();
    }
}
