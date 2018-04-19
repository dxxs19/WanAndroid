package com.wei.wanandroid.javasources.designpatterns.strategy;

/**
 * 策略模式定义了一系列的算法，并将每一个算法封装起来，而且使它们还可以相互替换。策略模式让算法独立于
 * 使用它的客户而独立变化。
 * 策略模式的行为是彼此独立、可相互替换的
 * @author: WEI
 * @date: 2018/4/19
 */
public class TestStrategy
{
    private static ICalculateStrategy mCalculateStrategy;

    public static void main(String[] args) {
        TestStrategy testStrategy = new TestStrategy();
        testStrategy.setCalculateStrategy(new TaxiStrategy());
        System.out.println("乘坐的士10公里花费：" + mCalculateStrategy.calculatePrice(10) + "元");
        testStrategy.setCalculateStrategy(new BusStrategy());
        System.out.println("乘坐公交车10公里花费：" + mCalculateStrategy.calculatePrice(10) + "元");
    }

    public void setCalculateStrategy(ICalculateStrategy calculateStrategy)
    {
        mCalculateStrategy = calculateStrategy;
    }
}
