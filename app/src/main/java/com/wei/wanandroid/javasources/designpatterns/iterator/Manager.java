package com.wei.wanandroid.javasources.designpatterns.iterator;

/**
 * 经理
 * @author: WEI
 * @date: 2018/4/20
 */
public class Manager extends ILeader {
    @Override
    protected void handle(int money) {
        System.out.println("经理批复了" + money + "元！");
    }

    @Override
    protected int limit() {
        return 15000;
    }
}
