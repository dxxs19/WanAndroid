package com.wei.wanandroid.javasources.designpatterns.observer;

/**
 * @author: WEI
 * @date: 2018/3/28
 */

public class TestObserver
{
    public static void main(String[] args) {
        Observer observer = new StudentObserver("西施");
        Observer observer1 = new StudentObserver("貂蝉");
        Observer observer2 = new StudentObserver("杨贵妃");
        Observer observer3 = new StudentObserver("王昭君");
        Observer observer4 = new StudentObserver("虞姬");
        Subject subject = new TeacherSubject();
        subject.addObserver(observer);
        subject.addObserver(observer1);
        subject.addObserver(observer2);
        subject.addObserver(observer3);
        subject.addObserver(observer4);
        subject.notifyAllObservers("I love you !");

        subject.deleObserver(observer2);
        subject.notifyAllObservers("我不喜欢杨贵妃！");
    }
}
