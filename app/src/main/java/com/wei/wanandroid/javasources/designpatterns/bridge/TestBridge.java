package com.wei.wanandroid.javasources.designpatterns.bridge;

/**
 * 桥接模式
 * 定义：将抽象部分与实现部分分离，使它们都可以独立地进行变化。
 * 作用：连接“抽象部分”与“实现部分”。事实上，任何多维度变化类或者说多个树状类之间的耦合都可以使用桥接模式来实现解耦。
 */
public class TestBridge {
    public static void main(String[] args) {
        CoffeeAdditives sugar = new Sugar();
        CoffeeAdditives ordinary = new Ordinary();

        Coffee coffee = new LargeCoffee(sugar);
        Coffee coffee1 = new SmallCoffee(sugar);
        Coffee coffee2 = new LargeCoffee(ordinary);
        Coffee coffee3 = new SmallCoffee(ordinary);

        coffee.makeCoffee();
        coffee1.makeCoffee();
        coffee2.makeCoffee();
        coffee3.makeCoffee();
    }
}
