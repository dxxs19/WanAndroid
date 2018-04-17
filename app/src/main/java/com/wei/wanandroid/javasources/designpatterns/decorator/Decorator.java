package com.wei.wanandroid.javasources.designpatterns.decorator;

/**
 * @author: WEI
 * @date: 2018/4/17
 */
public class Decorator implements Sourceable {
    private Sourceable mSourceable;

    public Decorator(Sourceable sourceable)
    {
        mSourceable = sourceable;
    }

    @Override
    public void method() {
        System.out.println("before decorator !");
        mSourceable.method();
        System.out.println("after decorator !");
    }
}
