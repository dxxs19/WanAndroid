package com.wei.wanandroid.javasources.designmode;

/**
 * 具体观察者(例如，学生)
 * @author: WEI
 * @date: 2017/11/27
 */

public class StudentObserver implements Observer
{
    private String mName;

    public StudentObserver(String name)
    {
        mName = name;
    }

    @Override
    public void update(String msg) {
        System.out.println(mName + " : 收到作业通知 - " + msg);
    }
}
