package com.wei.wanandroid.javasources.designpatterns.strategy;

/**
 * 计程车计价策略
 * @author: WEI
 * @date: 2018/4/19
 */
public class TaxiStrategy implements ICalculateStrategy {
    @Override
    public double calculatePrice(int km) {
        return km > 4 ? ( 10 + (km - 4) * 2.6 ) : 10;
    }
}
