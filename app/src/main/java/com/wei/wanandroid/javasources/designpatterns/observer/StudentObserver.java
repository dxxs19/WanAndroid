package com.wei.wanandroid.javasources.designpatterns.observer;


/**
 * 学生属于观察者
 * @author: WEI
 * @date: 2018/3/28
 */

public class StudentObserver implements Observer
{
    private String mName = null;

    public StudentObserver(String name)
    {
        mName = name;
    }

    @Override
    public void notify(String msg) {
        System.out.println(mName + " 收到老师的通知 ：" + msg);
    }
}
