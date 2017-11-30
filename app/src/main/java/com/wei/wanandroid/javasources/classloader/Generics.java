package com.wei.wanandroid.javasources.classloader;

/**
 * jdk1.5之后，java的Class类增加了泛型功能，从而允许使用泛型来限制Class类，例如，String.class的类型实际上
 * 是Class<String>。如果将Class对应的类暂时未知，则使用Class<?>。通过在反射中使用泛型，可以避免使用反射生成的
 * 对象需要强制类型转换。
 * @author: WEI
 * @date: 2017/11/30
 */

public class Generics
{
    public <T> T getInstance(Class<T> cls)
    {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Generics generics = new Generics();
        String s = generics.getInstance(String.class);
        System.out.println( "s : " + s.toString());
    }
}
