package com.wei.wanandroid.javasources.designpatterns.interpreter;

public class NumExpression extends ArithmeticExpression {
    int num;

    public NumExpression(int num)
    {
        this.num = num;
    }

    @Override
    public int interpreter() {
        return num;
    }
}
