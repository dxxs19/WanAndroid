package com.wei.wanandroid.javasources.designpatterns.factory.simplefactory;

/**
 * 具体产品
 * @author: WEI
 * @date: 2018/4/16
 */
public class AudiQ3 implements IAudiCar {
    @Override
    public void drive() {
        System.out.println("Q3启动了！");
    }

    @Override
    public void selfNavigation() {
        System.out.println("Q3开启自动巡航");
    }
}
