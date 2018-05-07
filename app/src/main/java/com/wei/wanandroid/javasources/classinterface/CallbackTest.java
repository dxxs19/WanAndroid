package com.wei.wanandroid.javasources.classinterface;

/**
 * @author: WEI
 * @date: 2018/5/7
 */
public class CallbackTest implements Callback
{
    public static void main(String[] args)
    {
        new CallbackTest().test();
    }

    void test()
    {
        B b = new B();
        b.setCallback(this);
        b.doSomething();
    }

    @Override
    public void execute() {
        System.out.println("收到了，下个月加薪！");
    }
}

class B
{
    private Callback mCallback;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void doSomething()
    {
        System.out.println("任务已圆满完成，现在向领导汇报！");
        mCallback.execute();
    }

}

interface Callback
{
    void execute();
}
