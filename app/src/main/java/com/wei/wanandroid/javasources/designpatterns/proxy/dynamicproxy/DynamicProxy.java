package com.wei.wanandroid.javasources.designpatterns.proxy.dynamicproxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: WEI
 * @date: 2018/4/27
 */
public class DynamicProxy implements InvocationHandler
{
    /** 被代理的类引用 **/
    private Object mObject;

    public DynamicProxy(Object obj)
    {
        mObject = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 调用被代理类对象的方法
        Object result = method.invoke(mObject, args);
        return result;
    }
}
