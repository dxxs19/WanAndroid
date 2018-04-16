package com.wei.wanandroid.javasources.designpatterns.factory.simplefactory;

/**
 * 具体产品
 * @author: WEI
 * @date: 2018/4/16
 */
public class AudiQ1 implements IAudiCar {
    @Override
    public void drive() {
        System.out.println("Q1启动了！");
    }

    @Override
    public void selfNavigation() {
        System.out.println("Q1开启自动巡航");
    }
}
