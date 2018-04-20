package com.wei.wanandroid.javasources.designpatterns.iterator;

/**
 * 主管
 * @author: WEI
 * @date: 2018/4/20
 */
public class Director extends ILeader {
    @Override
    protected void handle(int money) {
        System.out.println("主管批复了" + money + "元！");
    }

    @Override
    protected int limit() {
        return 10000;
    }
}
