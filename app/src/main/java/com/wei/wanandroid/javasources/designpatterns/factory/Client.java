package com.wei.wanandroid.javasources.designpatterns.factory;

import com.wei.wanandroid.javasources.designpatterns.factory.abstractfactory.AudiQ1Factory;
import com.wei.wanandroid.javasources.designpatterns.factory.abstractfactory.AudiQ7Factory;
import com.wei.wanandroid.javasources.designpatterns.factory.factorymethod.MutiFactory;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.SimpleFactory;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.IAudiCar;

/**
 * 工厂设计模式分为 工厂方法模式 和 抽象工厂模式。
 * 工厂方法模式分为三种：普通工厂模式，就是建立一个工厂类，对实现了同一接口的一些类进行实例的创建；
 *                       多个工厂方法模式，是对普通工厂方法模式的改进，在普通工厂方法模式中，如果传递的字符串出错，则不能正确创建对象，而多个工厂方法模式提供多个工厂方法，分别创建对象；
 *                       静态工厂方法模式，将上面的多个工厂方法模式里的方法置为静态的，不需要创建实例，直接调用即可。
 * 抽象工厂模式：工厂方法模式有一个问题就是，类的创建依赖工厂类，也就是说，如果想要拓展程序，必须对工厂类进行修改，这违背了闭包原则，所以，从设计角度考虑，有一定的问题，如何
 *               解决？就用到抽象工厂模式，创建多个工厂类，这样一旦需要增加新的功能，直接增加新的工厂类就可以了，不需要修改之前的代码。
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
