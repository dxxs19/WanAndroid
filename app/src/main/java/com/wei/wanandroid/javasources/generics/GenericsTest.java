package com.wei.wanandroid.javasources.generics;

import com.wei.wanandroid.javasources.classloader.Generics;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenericsTest
{
    private HashMap<String, Integer> mHashMap;

    public static void main(String[] args) {
        Apple apple = getInstance(Apple.class);
        System.out.println( apple.getInfo() );
        Generics generics = getInstance(Generics.class);
        System.out.println(generics.getResult());

        // 反射获取泛型类型
        Class<GenericsTest> genericsTestClass = GenericsTest.class;
        try {
            Field field = genericsTestClass.getDeclaredField("mHashMap");
            Class<?> a = field.getType();
            System.out.println("mHashMap 的类型是 ：" + a);
            // 取得字段的泛型类型
            Type type = field.getGenericType();
            // 如果type类型是ParameterizedType对象
            if (type instanceof ParameterizedType)
            {
                // 强制类型转换
                ParameterizedType parameterizedType = (ParameterizedType) type;
                // 获取原来类型
                Type type1 = parameterizedType.getRawType();
                System.out.println("原始类型是：" + type1);
                // 取得泛型类型的泛型参数
                Type[] tArgs = parameterizedType.getActualTypeArguments();
                System.out.println("泛型类型是：");
                for (int i = 0; i < tArgs.length; i++)
                {
                    System.out.println("第" + i + "个泛型类型是：" + tArgs[i]);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    public static <T> T getInstance(Class<T> tClass)
    {
        try {
            return tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
