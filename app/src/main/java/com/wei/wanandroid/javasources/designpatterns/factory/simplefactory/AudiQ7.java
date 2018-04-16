package com.wei.wanandroid.javasources.designpatterns.factory.simplefactory;

/**
 * @author: WEI
 * @date: 2018/4/16
 */
public class AudiQ7 implements IAudiCar {
    @Override
    public void drive() {
        System.out.println("Q7启动了！");
    }

    @Override
    public void selfNavigation() {
        System.out.println("Q7开启自动巡航");
    }
}
