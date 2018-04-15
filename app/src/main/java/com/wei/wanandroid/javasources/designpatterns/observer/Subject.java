package com.wei.wanandroid.javasources.designpatterns.observer;

/**
 * 主题
 * @author: WEI
 * @date: 2018/3/28
 */

public interface Subject {
    void addObserver(Observer observer);
    void deleObserver(Observer observer);
    void notifyAllObservers(String s);
}
