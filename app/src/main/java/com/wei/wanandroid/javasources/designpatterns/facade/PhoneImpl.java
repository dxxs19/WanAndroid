package com.wei.wanandroid.javasources.designpatterns.facade;

public class PhoneImpl implements Phone {
    @Override
    public void call() {
        System.out.println("打电话");
    }

    @Override
    public void hangup() {
        System.out.println("挂断");
    }
}
