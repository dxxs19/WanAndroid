package com.wei.wanandroid.javasources.designpatterns.bridge;

public class LargeCoffee extends Coffee {
    public LargeCoffee(CoffeeAdditives coffeeAdditives) {
        super(coffeeAdditives);
    }

    @Override
    public void makeCoffee() {
        System.out.println("大杯的" + coffeeAdditives + "咖啡");
    }
}
