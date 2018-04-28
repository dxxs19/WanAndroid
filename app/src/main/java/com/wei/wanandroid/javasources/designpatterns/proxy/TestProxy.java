package com.wei.wanandroid.javasources.designpatterns.proxy;

import com.wei.wanandroid.javasources.designpatterns.proxy.dynamicproxy.DynamicProxy;
import com.wei.wanandroid.javasources.designpatterns.proxy.staticproxy.ILawsuit;
import com.wei.wanandroid.javasources.designpatterns.proxy.staticproxy.Lawyer;
import com.wei.wanandroid.javasources.designpatterns.proxy.staticproxy.XiaoMin;

import java.lang.reflect.Proxy;

/**
 * @author: WEI
 * @date: 2018/4/27
 */
public class TestProxy {
    public static void main(String[] args) {
        ILawsuit xiaoMin = new XiaoMin();
        ILawsuit lawer = new Lawyer(xiaoMin);
        lawer.submit();
        lawer.burden();
        lawer.defend();
        lawer.finish();
        System.out.println("=========================");
        // 构造一个动态代理
        DynamicProxy dynamicProxy = new DynamicProxy(xiaoMin);
        // 获取被代理类小民的ClassLoader
        ClassLoader classLoader = xiaoMin.getClass().getClassLoader();
        // 动态构造一个代理者律师
        ILawsuit lawyer = (ILawsuit) Proxy.newProxyInstance(classLoader, new Class[]{ILawsuit.class}, dynamicProxy);
        lawyer.submit();
        lawyer.burden();
        lawyer.defend();
        lawyer.finish();
    }
}
