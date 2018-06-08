package com.wei.wanandroid.javasources.classloader;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: WEI
 * @date: 2018/6/7
 */
public class ReflectTest
{
    public static void main(String[] args)
    {
        ReflectTest reflectTest = new ReflectTest();
        try {
            reflectTest.testGainClass();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获得Class对象
     */
    private void testGainClass() throws Exception
    {
        Class aClass = Class.forName("com.wei.wanandroid.javasources.classloader.Generics");
        Class<Generics> aClass1 = Generics.class;
        // 返回true。说明是同一个Class对象
        System.out.println(aClass == aClass1);

        // 获取所有构造方法
        Constructor<?>[] constructors = aClass1.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors)
        {
            System.out.println(constructor);
        }

        // 反射调用方法
        Method method = aClass1.getMethod("setResult", int.class);
//        method.setAccessible(true);
        // 第一个参数为对象的实例，即一定要实例化，无参实例化方法为.newInstance();
        method.invoke(aClass1.newInstance(), 1111);

        // 利用默认构造函数实例化对象
        Generics generics = (Generics) aClass.newInstance();
        generics.setResult(100);
        System.out.println(generics.getResult());

        // 调用方法
        Method method1 = aClass1.getMethod("getResult");
        System.out.println(method1.invoke(aClass1.newInstance()));

        // 访问属性值
        Field resultField = aClass1.getDeclaredField("mResult");
        resultField.setAccessible(true);
        Generics generics1 = aClass1.newInstance();
        resultField.setInt(generics1, 1000);
        System.out.println(generics1.getResult());

        // 操作数组
        int length = 5;
        Object arry = Array.newInstance(int.class, length);
        Array.set(arry, 1, 10);
        Array.set(arry, 3, 30);
        for (int i = 0; i < length; i ++)
        {
            System.out.print(Array.get(arry, i) + ", ");
        }
    }
}
