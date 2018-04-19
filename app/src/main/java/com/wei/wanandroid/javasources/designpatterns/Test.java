package com.wei.wanandroid.javasources.designpatterns;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: WEI
 * @date: 2018/4/19
 */
public class Test {
    public static void main(String[] args) {
        Observable1 observable = new Observable1();
        Observer liuObs = new Observer1("刘小姐");
        observable.addObserver( liuObs );
        observable.addObserver(new Observer2());
        observable.addObserver(new Observer3());

        observable.publishMessageToAll("我爱你们！");

        observable.deleteObserver(liuObs);
        observable.publishMessageToAll("我把刘小姐删了！");
    }

}

class Observable1 extends Observable
{
    public void publishMessageToAll(String content)
    {
        // 标识状态或者内容发生改变，当用系统自带的观察者模式时，此行代码不能少
        setChanged();
        // 遍历把消息发送到所有观察者。触发它们的update（）。
        notifyObservers(content);
    }
}

class Observer1 implements Observer
{
    private String mName ;

    public Observer1(String name) {
        mName = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println( mName + "收到信息 ：" + arg);
    }
}

class Observer2 implements Observer
{
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Observer2收到信息 ：" + arg);
    }
}

class Observer3 implements Observer
{
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Observer3收到信息 ：" + arg);
    }
}
