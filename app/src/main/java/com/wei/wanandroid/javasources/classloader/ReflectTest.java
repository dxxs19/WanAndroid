package com.wei.wanandroid.javasources.classloader;

import java.lang.reflect.Constructor;

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
        Constructor<?>[] constructors = aClass1.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors)
        {
            System.out.println(constructor);
        }

//        Constructor constructor = aClass1.getConstructor(Generics.class);
//        System.out.println(constructor);
    }
}
