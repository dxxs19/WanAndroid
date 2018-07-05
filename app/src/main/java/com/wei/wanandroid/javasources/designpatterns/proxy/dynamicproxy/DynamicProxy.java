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

    /**
     *
     * @param proxy    指代我们所代理的那个真实对象
     * @param method   指代的是我们所要调用真实对象的某个方法的Method对象
     * @param args     指代的是调用真实对象某个方法时接受的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        //　　在代理真实对象前我们可以添加一些自己的操作
        System.out.println("before ...");
        System.out.println("Method:" + method);
        System.out.println("method.getDeclaringClass() : " + method.getDeclaringClass());
        System.out.println("method.getAnnotations() : " + method.getAnnotations());
        System.out.println("obj = " + mObject + ", args = " + args);

        // 调用被代理类对象的方法; 当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        Object result = method.invoke(mObject, args);

        System.out.println("after...\n");
        return result;
    }
}
