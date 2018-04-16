package com.wei.wanandroid.javasources.designpatterns.factory.staticfactory;

import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.AudiQ1;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.AudiQ3;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.AudiQ5;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.AudiQ7;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.IAudiCar;

/**
 * 静态工厂模式，就是把多工厂方法模式中每个创建实例方法加上static关键字。即把创建实例方法置为静态的。
 * @author: WEI
 * @date: 2018/4/16
 */
public class StaticFactory {
    public static IAudiCar createAudiQ1()
    {
        return new AudiQ1();
    }

    public static IAudiCar createAudiQ3()
    {
        return new AudiQ3();
    }

    public static IAudiCar createAudiQ5()
    {
        return new AudiQ5();
    }

    public static IAudiCar createAudiQ7()
    {
        return new AudiQ7();
    }
}
