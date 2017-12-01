package com.wei.wanandroid.javasources.designmode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: WEI
 * @date: 2017/11/30
 */

public class NumOrder
{
    private static String a = null;

    public static void main(String[] args)
    {
        NumOrder numOrder = new NumOrder();
        System.out.println(numOrder.order(15734));
        String b = a;
        System.out.println(a + "");
        System.out.println( (a + "").equals(a) );
        System.out.println( numOrder.getTopNum(15987, 1 ) );
        System.out.println( numOrder.numArray.toArray() );
    }

    List numArray = new ArrayList();
    private int getTopNum(int num, int rate)
    {
        int top = num/rate;
        if (top > 0 && top < 9)
        {
            System.out.println("top = " + top);
            numArray.add(top);
            if (rate > 1)
            {
                getTopNum(num%rate, rate/10);
            }
        }
        else
        {
            getTopNum(num, rate * 10);
        }
        return top;
    }

    private int order(int num)
    {
        int firstNum = num / 10000;
        int secondNum = ( num % 10000 ) / 1000;
        int thirdNum = ( num % 1000 ) / 100;
        int forthNum = ( num % 100) / 10;
        int fifthNum = ( num % 10) / 1;
        int[] array = {firstNum, secondNum, thirdNum, forthNum, fifthNum};
        int size = array.length , temp = 0;
        for (int i = 1 ; i < size ; i ++)
        {
            for (int j = i; j <= size-i; j ++)
            {
                if (array[j] < array[j - 1])
                {
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
        return array[size-1]*10000 + array[size-2]*1000 + array[size-3] * 100 + array[size-4] * 10 + array[0] ;
    }
}
