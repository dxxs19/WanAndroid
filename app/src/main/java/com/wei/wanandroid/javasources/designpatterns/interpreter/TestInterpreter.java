package com.wei.wanandroid.javasources.designpatterns.interpreter;

public class TestInterpreter {
    public static void main(String[] args)
    {
        String calExpression = "22 + 23 - 11 + 9 - 3";
        Calculator calculator = new Calculator(calExpression);
        System.out.println(calExpression + " = " + calculator.calculate());
    }
}
