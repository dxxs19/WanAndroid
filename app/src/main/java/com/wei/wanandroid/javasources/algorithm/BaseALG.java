package com.wei.wanandroid.javasources.algorithm;

/**
 * @author: WEI
 * @date: 2017/12/8
 */

public class BaseALG
{
    protected void printArray(int[] array)
    {
        System.out.print("{");
        int len = array.length;
        for (int i = 0; i < len; i ++)
        {
            System.out.print(array[i]);
            if (i < len - 1)
            {
                System.out.print(", ");
            }
        }
        System.out.println("}");
    }
}
