package com.wei.wanandroid.javasources.designpatterns.factory.factorymethod;

import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.AudiQ1;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.AudiQ3;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.AudiQ5;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.AudiQ7;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.IAudiCar;

/**
 * 提供多个工厂方法，分别创建对象。
 * 容错性好。简单工厂模式中，传入的类别如果不存在，就无法创建实例。多工厂方法模式不存在这种问题。
 * 扩展性好，增加实例时，只需加一个创建实例的函数 + 新建一个具体产品类
 *
 * 多工厂方法模式由三部分组成：具体工厂（本类）、具体产品（AudiQ1、Q3、Q5）和抽象产品（IAudiCar）。
 *
 * @author: WEI
 * @date: 2018/4/16
 */
public class MutiFactory {
    public IAudiCar createAudiQ1()
    {
        return new AudiQ1();
    }

    public IAudiCar createAudiQ3()
    {
        return new AudiQ3();
    }

    public IAudiCar createAudiQ5()
    {
        return new AudiQ5();
    }

    public IAudiCar createAudiQ7()
    {
        return new AudiQ7();
    }
}
