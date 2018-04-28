package com.wei.wanandroid.javasources.designpatterns.proxy.staticproxy;

/**
 * 律师，代小明打官司。即代理者。
 * @author: WEI
 * @date: 2018/4/27
 */
public class Lawyer implements ILawsuit
{
    ILawsuit mLawsuit;

    public Lawyer(ILawsuit iLawsuit)
    {
        mLawsuit = iLawsuit;
    }

    @Override
    public void submit() {
        mLawsuit.submit();
    }

    @Override
    public void burden() {
        mLawsuit.burden();
    }

    @Override
    public void defend() {
        mLawsuit.defend();
    }

    @Override
    public void finish() {
        mLawsuit.finish();
    }
}
