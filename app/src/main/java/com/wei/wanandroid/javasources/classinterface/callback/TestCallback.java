package com.wei.wanandroid.javasources.classinterface.callback;

/**
 * @author: WEI
 * @date: 2018/5/7
 */
public class TestCallback {
    public static void main(String[] args) {
        Employee employee = new Employee();
        employee.setCallBackInterface(new Boss());
        employee.doSomething();
    }
}
