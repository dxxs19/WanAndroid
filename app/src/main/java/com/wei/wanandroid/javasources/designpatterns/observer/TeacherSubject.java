package com.wei.wanandroid.javasources.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 老师属于被观察者，一旦老师有动作，就会通知所有学生
 * @author: WEI
 * @date: 2018/3/28
 */

public class TeacherSubject implements Subject
{
    List<Observer> mObservers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        mObservers.add(observer);
    }

    @Override
    public void deleObserver(Observer observer) {
        mObservers.remove(observer);
    }

    @Override
    public void notifyAllObservers(String s) {
        for (Observer observer : mObservers)
        {
            observer.notify(s);
        }
    }
}
