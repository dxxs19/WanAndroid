package com.wei.wanandroid.javasources.classinterface;

import java.lang.reflect.Method;

/**
 * 反射相关
 * @author: WEI
 * @date: 2018/3/8
 */

public class ReflectTest
{
    public static void main(String[] args) {
        ReflectTest reflectTest = new ReflectTest();
        reflectTest.relect();


    }

    public void say()
    {
        System.out.println("Say Hello!");
    }

    private void test()
    {
        System.out.println("test");
    }

    private void relect()
    {
        System.out.println("class : " + getClass() + ", name : " + getClass().getName() + ", superclass : " + getClass().getSuperclass());
        Method[] methods = getClass().getMethods();
        if (methods != null && methods.length > 0)
        {
             for (Method method : methods)
             {
                 StringBuilder builder = new StringBuilder();
                 builder.append(method.getModifiers())
                         .append(method.getParameterTypes());
                 Class<?>[] parameterTypes = method.getParameterTypes();
                 if (parameterTypes.length > 0)
                 {
                     for (Class<?> aClass : parameterTypes)
                     {
                         System.out.println( method.getName() + " , parameterType : " + aClass.getName() + ", " + parameterTypes.length);
                     }
                 }
                 System.out.println(method.getName() + " : " + builder );
             }
        }
    }
}
