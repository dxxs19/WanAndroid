package com.wei.wanandroid.javasources.algorithm;

/**
 * Created by Administrator on 2018-03-05.
 */

public class QuickSort extends BaseALG
{
    public static void main(String[] args) {
        int a[] = {5, 4, 8, 7, 9, 3, 1};
        QuickSort quickSort = new QuickSort();
        quickSort.printArray(a);
        quickSort.quickSort(a, 0, a.length-1);
        quickSort.printArray(a);
    }

    private void quickSort(int[] arr,int low,int high) {
        int i , j , temp , t ;
        while ( low > high )
        {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = arr[low];

        while (i < j)
        {
            // 从右往左，找到小于temp的数
            while ( arr[j] >= temp && i < j )
            {
                j --;
            }
            // 从左往右，找到大于temp的数
            while ( arr[i] <= temp && i < j )
            {
                i ++;
            }

            // 将找到的两个数交换位置
            if ( i < j ) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }

        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j - 1);
        //递归调用右半数组
        quickSort(arr, j + 1, high);
    }
}
