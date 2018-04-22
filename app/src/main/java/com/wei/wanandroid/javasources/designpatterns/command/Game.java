package com.wei.wanandroid.javasources.designpatterns.command;

/**
 * 游戏类，真正处理各种操作逻辑
 */
public class Game {

    public void toLeft()
    {
        System.out.println("向左");
    }

    public void toRight()
    {
        System.out.println("向右");
    }
}
