package com.wei.wanandroid.javasources.algorithm;

/**
 * 高级排序
 * @author: WEI
 * @date: 2018/3/5
 */

public class AdvancedSort extends BaseALG
{
    public static void main(String[] args)
    {
        int a[] = {4, 8, 9, 1, 10, 6, 2, 5};
        AdvancedSort advancedSort = new AdvancedSort();
        advancedSort.printArray(a);
//        advancedSort.quickSort(a, 0, a.length-1);
        advancedSort.mergeSort(a, 0, a.length - 1);
        advancedSort.printArray(a);
    }

    /**
     * 快速排序
     * @param arr
     * @param low
     * @param high
     */
    private void quickSort(int[] arr, int low, int high) {
        int i, j, temp, t;
        if (low >= high)
        {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = arr[low];

        while ( i < j )
        {
            //先看右边，依次往左递减。找到一个小于temp的数（小于基准的数应该放在左边）
            while ( temp <= arr[j] && i < j )
            {
                j --;
            }

            //再看左边，依次往右递增。找到一个大于temp的数（大于基准的数应该放在右边）
            while ( temp >= arr[i] && i < j )
            {
                i ++;
            }
            //如果满足条件则交换
            if ( i < j )
            {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j - 1 );   // 关键点，是j-1，不是 i - 1
        //递归调用右半数组
        quickSort(arr, j + 1, high);   // 关键点，是j+1，不是 i + 1
    }

    /**
     * 归并排序
     * @param arr
     * @param start
     * @param end
     */
    private void mergeSort(int[] arr, int start, int end)
    {
        if (start >= end)
        {
            return;
        }
        int mid = ( start + end ) / 2;
        int start1 = start;
        int end1 = mid;
        int start2 = mid + 1;
        int end2 = end;
        mergeSort(arr, start1, end1);
        mergeSort(arr, start2, end2);
        merge(arr, start1, end1, start2, end2);
    }

    // 合并两个有序数组为一个有序数组
    private void merge(int[] arr, int start1, int end1, int start2, int end2) {
        int start = start1;
        // 临时数组
        int[] t = new int[end2 - start1 + 1];
        // 临时数组下标
        int i = 0;

        // 比较开始
        while ( start1 <= end1 && start2 <= end2 )
        {
            if (arr[start1] < arr[start2])
            {
                t[i] = arr[start1];
                start1 ++;
                i ++;
            }
            if (arr[start1] > arr[start2])
            {
                t[i] = arr[start2];
                start2 ++;
                i ++;
            }
        }

        // 拷贝剩余元素
        while (start1 <= end1)
        {
            t[i] = arr[start1];
            start1 ++;
            i ++;
        }

        // 拷贝剩余元素
        while (start2 <= end2)
        {
            t[i] = arr[start2];
            start2 ++;
            i ++;
        }

        // 将已排好序的元素放回对应位置
        for (i = 0; i < t.length; i ++)
        {
            arr[start + i] = t[i];
        }
        printArray(arr);
    }
}
