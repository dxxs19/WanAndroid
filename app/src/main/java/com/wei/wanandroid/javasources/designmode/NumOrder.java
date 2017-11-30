package com.wei.wanandroid.javasources.designmode;

/**
 * @author: WEI
 * @date: 2017/11/30
 */

public class NumOrder
{
    public static void main(String[] args) {
        System.out.println(new NumOrder().order(15734));
    }

    private int getTopNum(int num)
    {
        return 0;
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
