package com.wei.wanandroid.javasources.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 此时泛型的形参一定要是一个具体的类型，如String；
 * 也可以把形参去掉
 */
public class Apple extends Fruit<String>
{

    public Apple(String s) {
        super(s);
    }

    /**
     * 重写（覆盖）了父类的方法
      */
    @Override
    public String getInfo() {
        return "雪梨";
    }

    public static void main(String[] args) {
        Fruit<String> fruit = new Apple("苹果");
        System.out.println( fruit.getInfo() );

//        List<? extends Number> fruits = new ArrayList<>();
//        fruits.add(0, fruit);
        List<?> objects = new ArrayList<>();
    }

    public static <T> void copy(Collection<T> dest, Collection<? extends T> src)
    {
        for (T ele : src)
        {
            dest.add(ele);
        }
    }
}
