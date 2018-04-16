package com.wei.wanandroid.javasources.designpatterns.factory;

import com.wei.wanandroid.javasources.designpatterns.factory.abstractfactory.AudiQ1Factory;
import com.wei.wanandroid.javasources.designpatterns.factory.abstractfactory.AudiQ7Factory;
import com.wei.wanandroid.javasources.designpatterns.factory.factorymethod.MutiFactory;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.SimpleFactory;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.IAudiCar;

/**
 * @author: WEI
 * @date: 2018/4/16
 */
public class Client
{
    public static void main(String[] args)
    {
//        IAudiCar audiQ1 = SimpleFactory.getTargetCar("Q1");
//        IAudiCar audiQ1 = new MutiFactory().createAudiQ1();
        IAudiCar audiQ1 = new AudiQ1Factory().create();
        audiQ1.drive();
        audiQ1.selfNavigation();

//        IAudiCar audiQ3 = SimpleFactory.getTargetCar("Q3");
        IAudiCar audiQ3 = new MutiFactory().createAudiQ3();
        audiQ3.drive();
        audiQ3.selfNavigation();

//        IAudiCar audiQ5 = SimpleFactory.getTargetCar("Q5");
        IAudiCar audiQ5 = new MutiFactory().createAudiQ5();
        audiQ5.drive();
        audiQ5.selfNavigation();

//        IAudiCar audiQ7 = new MutiFactory().createAudiQ7();
        IAudiCar audiQ7 = new AudiQ7Factory().create();
        audiQ7.drive();
        audiQ7.selfNavigation();

    }
}
