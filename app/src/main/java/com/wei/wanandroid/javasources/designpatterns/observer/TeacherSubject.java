package com.wei.wanandroid.javasources.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 老师属于被观察者，一旦老师有动作，就会通知所有学生
 * @author: WEI
 * @date: 2018/3/28
 */

public class TeacherSubject implements Subject
{
    Vector<Observer> mObservers = new Vector<>();

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
