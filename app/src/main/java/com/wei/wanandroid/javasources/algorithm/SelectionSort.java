package com.wei.wanandroid.javasources.algorithm;

/**
 * 选择和冒泡排序
 * 选择排序的数组也分为已排序部分和未排序部分
 * 假设数据总数为n，则为了搜索未排序部分最小的的值需要(n-1)+(n-2)+(n-3)+……+1次比较，也就是n²/2+n/2次比较，因此时间复杂度为O(n²)。
 * 同样的，此前讲过的插入排序和冒泡排序的时间复杂度也是O(n²)。它们的区别就是：不含flag的冒泡算法和选择排序并不依赖于比较运算的次数，
 * 不受输入数据的影响，而插入算法却依赖于比较运算的次数，处理某些数据时会具有很高的效率。
 * @author: WEI
 * @date: 2017/12/8
 */

public class SelectionSort extends BaseALG
{
    public static void main(String[] args) {
        int a[] = {5, 4, 8, 7, 9, 3, 1};
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.printArray(a);
//        selectionSort.selectionSort(a);
//        selectionSort.bubbleSort(a);
//        selectionSort.selectionSortTest(a);
        selectionSort.bubbleSortTest(a);
        selectionSort.printArray(a);
    }

    /**
     * 冒泡排序测试
     * 每次都从头开始比较，因为前面是未排序的，最后面的是已排序的
     * @param a
     */
    private void bubbleSortTest(int[] a) {
        int len = a.length;
        int i, j, temp;
        for ( i = 0; i < len; i ++)
        {
            // 每次都从头开始比较，因为前面是未排序的，最后面的是已排序的
            for ( j = 1; j < len - i; j ++)
            {
                if (a[j - 1] > a[j])
                {
                    temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序测试
     * @param a
     */
    private void selectionSortTest(int[] a)
    {
        int len = a.length;
        int i, j, minIndex, temp;
        for (i = 0; i < len; i ++)
        {
            minIndex = i;
            for (j = i + 1; j < len; j ++)
            {
                // 此处是a[minIndex]（不是a[i]）与a[j]比较
                if (a[minIndex] > a[j])
                {
                    minIndex = j;
                }
            }
            temp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = temp;
        }
    }

    /**
     * 选择排序（Selection sort）是一种简单直观的排序算法。它的工作原理如下。首先在未排序序列中找到最小（大）元素，
     * 存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * 以此类推，直到所有元素均排序完毕。
     * 选择排序的规则
     * 就是重复执行以下的处理：
     * 1.找出未排序部分最小值的位置min。
     * 2.将min位置的元素与未排序部分的起始元素做对比，如果顺序错误则将它们进行交换。
     * @param a
     */
    private void selectionSort(int[] a)
    {
        int len = a.length;
        int i, j, min, temp;
        for (i = 0; i < len; i ++)
        { // 每次将未排序部分的首元素下标赋值给下标min
            min = i;
            // 1.找出未排序部分最小值的位置min。
            for (j = i + 1 ; j < len ; j ++)
            {
                if (a[j] < a[min])
                {
                    min = j;
                }
            }
            temp = a[i];
            a[i] = a[min];
            a[min] = temp;
        }
    }



    /**
     * 最坏的情况下，冒泡排序对未排序部分的相邻元素进行了(n-1)+(n-2)+(n-3)+……+1次比较，也就是n²/2-n/2次比较，
     * 根据推导大O阶的规则我们得出冒泡排序的时间复杂度为O(n²)。
     * @param a
     */
    private void bubbleSort(int[] a)
    {
        int i, j, temp, len = a.length, count = 0;
//        for (i = len - 1; i > 0; i --)
//        {
//            for (j = 0; j < i; j ++)
//            {
//            count ++;
//                if (a[j + 1] < a[j])
//                {
//                    temp = a[j];
//                    a[j] = a[j + 1];
//                    a[j + 1] = temp;
//                }
//            }
//        }

        for ( i = 0; i < len; i ++)
        {
            for (j = 1; j < len - i; j ++)
            {   // 每次都从头开始比较，因为前面是未排序的，最后面的是已排序的
                count ++;
                if (a[j] < a[j - 1])
                {
                    temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                }
            }
        }
        System.out.println("count = " + count);
    }
}
