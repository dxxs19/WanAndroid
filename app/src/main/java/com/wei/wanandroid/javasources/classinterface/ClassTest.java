package com.wei.wanandroid.javasources.classinterface;

import java.lang.reflect.Method;

/**
 * @author: WEI
 * @date: 2018/7/16
 */
public class ClassTest {
    public static void main(String[] args) {
        ClassTest classTest = new ClassTest();
        classTest.testGetClass();
        classTest.testGetClassGetName();
    }

    private void testGetClass() {
        // getClass : class com.wei.wanandroid.javasources.classinterface.ClassTest
        System.out.println("getClass() : " + this.getClass());
        System.out.println("getClass().getSuperclass() : " + this.getClass().getSuperclass());
        // Object.class.getSuperclass() : null; Object 没有父类，返回空
        System.out.println("Object.class.getSuperclass() : " + Object.class.getSuperclass());
    }

    private void testGetClassGetName() {
        // getClass().getName : com.wei.wanandroid.javasources.classinterface.ClassTest
        System.out.println("getClass().getName() : " + getClass().getName());
        Method[] methods = getClass().getMethods();
        if (methods != null)
        {
            for (Method method:methods)
            {
                System.out.println(method.getName() + ", " + method.toString());
                Class<?>[] typeClasses = method.getParameterTypes();
                for (Class<?> aClass:typeClasses) {
                    System.out.println(aClass + ", " + aClass.getName());
                }
            }
        }
    }
}
