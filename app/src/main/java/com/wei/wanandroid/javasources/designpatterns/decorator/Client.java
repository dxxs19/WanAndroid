package com.wei.wanandroid.javasources.designpatterns.decorator;

/**
 * 装饰模式：顾名思义，装饰模式就是给一个对象增加一些新的功能，而且是动态的，要求装饰对象和被装饰对象实现同一个接口，装饰
 * 对象持有被装饰对象的实例。就增加功能来说，装饰模式相比生成子类更为灵活。
 * @author: WEI
 * @date: 2018/4/17
 */
public class Client {
    public static void main(String[] args) {
        Sourceable sourceable = new Decorator(new Source());
        sourceable.method();
    }
}
