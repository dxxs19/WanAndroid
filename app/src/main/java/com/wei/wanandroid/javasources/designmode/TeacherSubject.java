package com.wei.wanandroid.javasources.designmode;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体被观察者(例如，老师)
 * @author: WEI
 * @date: 2017/11/27
 */

public class TeacherSubject implements Subject
{
    List<Observer> mObservers = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        mObservers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        mObservers.remove(observer);
    }

    @Override
    public void notify(String msg) {
        for (Observer observer:mObservers) {
            observer.update(msg);
        }
    }
}
