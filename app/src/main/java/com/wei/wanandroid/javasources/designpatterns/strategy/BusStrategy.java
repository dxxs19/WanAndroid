package com.wei.wanandroid.javasources.designpatterns.strategy;

/**
 * 公交车计价策略
 * @author: WEI
 * @date: 2018/4/19
 */
public class BusStrategy implements ICalculateStrategy {
    @Override
    public double calculatePrice(int km) {
        return 2;
    }
}
