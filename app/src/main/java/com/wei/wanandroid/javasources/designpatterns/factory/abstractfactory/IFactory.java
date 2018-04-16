package com.wei.wanandroid.javasources.designpatterns.factory.abstractfactory;

import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.IAudiCar;

/**
 * 抽象工厂模式由四部分组成：抽象工厂（本类）、具体工厂（AudiQ1Factory、AudiQ7Factory）、具体产品（AudiQ1、Q3、Q5）和抽象产品（IAudiCar）。
 * @author: WEI
 * @date: 2018/4/16
 */
public interface IFactory {
    IAudiCar create();
}
