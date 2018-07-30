package com.wei.wanandroid.javasources.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 随机遍历
 * Created by Administrator on 2018-03-18.
 */

public class RandomTraversal extends BaseALG
{
    public static void main(String[] args) {
        int a[] = {5, 4, 8, 7, 9, 3, 1};
        RandomTraversal randomTraversal = new RandomTraversal();
        randomTraversal.randomTra(a);
    }

    private void randomTra(int[] arr) {
        List list = new ArrayList();
        int len = arr.length, randomIndex = 0;
        for (int a : arr)
        {
            list.add(a);
        }
        Random random = new Random();
        for (int i = 0; i < len; i ++)
        {
            randomIndex = random.nextInt( list.size() );//(int) (Math.random() * len - 1);
            Object a = list.remove(randomIndex);
            System.out.print(a + ", ");
        }

    }
}
