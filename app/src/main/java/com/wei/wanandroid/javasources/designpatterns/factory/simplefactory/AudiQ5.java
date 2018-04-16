package com.wei.wanandroid.javasources.designpatterns.factory.simplefactory;

/**
 * 具体产品
 * @author: WEI
 * @date: 2018/4/16
 */
public class AudiQ5 implements IAudiCar {
    @Override
    public void drive() {
        System.out.println("Q5启动了！");
    }

    @Override
    public void selfNavigation() {
        System.out.println("Q5开启自动巡航");
    }
}
