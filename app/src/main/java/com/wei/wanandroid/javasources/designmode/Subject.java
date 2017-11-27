package com.wei.wanandroid.javasources.designmode;

/**
 * 被观察者
 * @author: WEI
 * @date: 2017/11/27
 */

public interface Subject
{
    void attach(Observer observer);
    void detach(Observer observer);
    void notify(String msg);
}
