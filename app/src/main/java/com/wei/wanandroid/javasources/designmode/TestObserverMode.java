package com.wei.wanandroid.javasources.designmode;

/**
 * @author: WEI
 * @date: 2017/11/27
 */

public class TestObserverMode
{
    public static void main(String[] args) {
        TeacherSubject teacherSubject = new TeacherSubject();
        StudentObserver studentObserver = new StudentObserver("小明");
        teacherSubject.attach(studentObserver);
        teacherSubject.attach(new StudentObserver("小强"));
        teacherSubject.attach(new StudentObserver("小红"));
        teacherSubject.notify("今天的语文作业是写一篇关于天空的作文！");

        teacherSubject.detach(studentObserver);
        teacherSubject.notify("这次作业小明不用做！");


//        for (;;)
//        while (true)
        {  // 死循环,效果一致
            System.out.println("for recycle");
        }
    }
}
