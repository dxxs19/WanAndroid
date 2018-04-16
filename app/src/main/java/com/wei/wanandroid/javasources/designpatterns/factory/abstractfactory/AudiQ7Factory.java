package com.wei.wanandroid.javasources.designpatterns.factory.abstractfactory;

import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.AudiQ7;
import com.wei.wanandroid.javasources.designpatterns.factory.simplefactory.IAudiCar;

/**
 * @author: WEI
 * @date: 2018/4/16
 */
public class AudiQ7Factory implements IFactory {
    @Override
    public IAudiCar create() {
        return new AudiQ7();
    }
}
