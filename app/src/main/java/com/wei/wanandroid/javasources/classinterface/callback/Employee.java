package com.wei.wanandroid.javasources.classinterface.callback;

/**
 * @author: WEI
 * @date: 2018/5/7
 */
public class Employee
{
    private CallBackInterface mCallBackInterface;

    public void setCallBackInterface(CallBackInterface callBackInterface) {
        mCallBackInterface = callBackInterface;
    }

    public void doSomething()
    {
        int i = 0;
        while (i < 10)
        {
            System.out.println(i);
            i ++;
        }
        mCallBackInterface.execute();
    }
}
