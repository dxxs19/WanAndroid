package com.wei.wanandroid.javasources.designpatterns.factory.simplefactory;

/**
 * 简单工厂模式(Simple Factory Pattern)：又称为静态工厂方法(Static Factory Method)模式，它属于类创建型模式
 * （同属于创建型模式的还有工厂方法模式，抽象工厂模式，单例模式，建造者模式）。
 * 在简单工厂模式中，可以根据参数的不同返回不同类的实例。简单工厂模式专门定义一个类来负责创建其他类的实例，被创建的实例通常都具有共同的父类。
 *
 * 简单工厂模式由三部分组成：具体工厂（本类）、具体产品（AudiQ1、Q3、Q5）和抽象产品（IAudiCar）。
 *
 * @author: WEI
 * @date: 2018/4/16
 */
public class SimpleFactory {
    /**
     * 此方法通常是静态的，故也称静态工厂方法
     * @param type
     * @return
     */
    public static IAudiCar getTargetCar(String type) {
        switch (type) {
            case "Q1":
                return new AudiQ1();

            case "Q3":
                return new AudiQ3();

            case "Q5":
                return new AudiQ5();

            default:
                return null;
        }
    }
}
