package com.wei.wanandroid.javasources.designpatterns.iterator;

/**
 * 组长
 * @author: WEI
 * @date: 2018/4/20
 */
public class TeamLeader extends ILeader {
    @Override
    protected void handle(int money) {
        System.out.println("组长批复了" + money + "元！");
    }

    @Override
    protected int limit() {
        return 5000;
    }
}
