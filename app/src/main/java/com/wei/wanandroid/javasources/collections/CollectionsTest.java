package com.wei.wanandroid.javasources.collections;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: WEI
 * @date: 2017/11/9
 */
public class CollectionsTest
{

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("1");
        list.add("a");
        list.add("q");
        list.add("k");
        list.add("b");
        System.out.println("removed ,result = " + list);
        String[] destArray = new String[list.size()];
        System.arraycopy(list.toArray(), 0, destArray, 0, list.size());
        System.out.println(Arrays.asList(destArray));

        System.out.println(Integer.toBinaryString(100));
        // 右移n位相当于除以2的n次方，取整。例，100右移2位相当于100/4 = 25。
        System.out.println(100 >> 2);
        // 左移n位相当于乘以2的n次方。例，100左移2位相当于100*4 = 400。
        System.out.println(100 << 2);

        String aaa = null;
        System.out.println(!"".equals(aaa));

        String[] strings = (String[]) list.toArray(new String[list.size()]);
        for (String s:strings) {
            System.out.println(s);
        }
    }

}
