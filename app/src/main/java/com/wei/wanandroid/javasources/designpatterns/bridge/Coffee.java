package com.wei.wanandroid.javasources.designpatterns.bridge;

public abstract class Coffee {
    protected CoffeeAdditives coffeeAdditives;

    public Coffee(CoffeeAdditives coffeeAdditives)
    {
        this.coffeeAdditives = coffeeAdditives;
    }

    public abstract void makeCoffee();
}
