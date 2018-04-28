package com.wei.wanandroid.javasources.designpatterns.iterator;

/**
 * 责任链模式
 * @author: WEI
 * @date: 2018/4/20
 */
public class TestIterator {
    public static void main(String[] args) {
        ILeader teamLeader = new TeamLeader();
        ILeader director = new Director();
        ILeader manager = new Manager();

        teamLeader.mNextHandler = director;
        director.mNextHandler = manager;

        teamLeader.handleRequest(5000);
    }
}
