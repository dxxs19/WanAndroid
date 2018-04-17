package com.wei.wanandroid.javasources.designpatterns.decorator;

/**
 * @author: WEI
 * @date: 2018/4/17
 */
public class Source implements Sourceable {
    @Override
    public void method() {
        System.out.println("This is original method!");
    }
}
