package com.wei.wanandroid.javasources.generics;

public class Fruit<T>
{
    private T mInfo;

    public Fruit (T t)
    {
        mInfo = t;
    }

    public void setInfo(T info)
    {
        mInfo = info;
    }

    public T getInfo()
    {
        return mInfo;
    }
}
