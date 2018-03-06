package com.wei.wanandroid.javasources.algorithm;

/**
 * 插入排序和希尔排序
 * 分为已排序部分及未排序部分。从未排序部分取值依次和已排序部分比较，找到合适位置，插入
 * @author: WEI
 * @date: 2017/12/8
 */

public class InsertSort extends BaseALG
{
    public static void main(String[] args)
    {
        int a[] = {4, 8, 9, 1, 10, 6, 2, 5};
        InsertSort insertSort = new InsertSort();
        insertSort.printArray(a);
//        insertSort.insert(a);
//        insertSort.shellSort(a);
        insertSort.insertTest(a);
        insertSort.printArray(a);
    }

    private void insertTest(int[] a) {
        int len = a.length, i, j, temp;
        for ( i = 1; i < len; i ++)
        {
            temp = a[i];
            j = i - 1;
            while (j >= 0 && a[j] > temp)
            {
                a[j+1] = a[j];
                j --;
            }
            a[j+1] = temp;
        }
    }

    /**
     * i代表未排序部分的开头元素，temp是临时保存a[i]值的变量， j代表已排序部分temp要插入的位置。
     * 根据定义的这三个变量，插入排序的实现思路就是：外层循环i从1开始自增，并在每次循环开始时将a[i]的值保存在temp中；
     * 内层循环则是j从i-1开始向前自减，并将比temp大的元素从a[j]移动到a[j+1]，并将temp插入到当前j+1的位置
     * （内层循环后，j会先自减1，因此插入的地方则是j+1的位置），当j等于-1或者a[j]小于等于temp则内层循环结束。
     *
     * 在最坏的情况下，每个i循环都需要执行i次移动，总共需要1+2+……+n-1=n²/2+n/2，根据此前讲过的推导大O阶的规则的我们得出插入排序的时间复杂度为O(n²)。
     * @param a
     */
    private void insert(int[] a)
    {
        int len = a.length, count = 0;
        int i, j, temp;
        //增量为1的插入排序
        for (i = 1 ; i < len ; i ++)
        {
            temp = a[i];
            j = i - 1;
            while ( j >= 0 && a[j] > temp)
            {
                a[j + 1] = a[j];
                j --;
                count ++;
            }
            a[ j + 1 ] = temp;
        }
        System.out.println("count = " + count);
    }

    /**
     * 希尔排序改进了插入排序这一问题，它交换不相邻的元素对数组进行局部排序，并最终用插入排序将局部有序的数组进行排序。
     * 希尔排序的思想就是使得数组中任意间隔h的元素都是有序的，这样的数组可以成为h有序数组。这里拿数组a={4,8,9,1,10,6,2,5}为例，当h为4时，会将这个数组分为h个子数组。
     * 从上图可以看到，我们根据h=4，将数组分为了四个子数组，分别是{4,10}、{8,2}、{1,5}、{10,3}。我们分别对这四个子数组进行局部排序，接下来对h进行递减操作，直到h为1，
     * 这样最后一次循环就是一个典型的插入排序。
     *
     * 希尔排序的复杂度要根据h的值来进行计算，不同的h值会导致不同的复杂度，一般情况下，当h = 3 * h + 1时，希尔排序的复杂度基本维持在O(n^1.25)。
     * @param a
     */
    private void shellSort(int[] a)
    {
        int h = 1;
        int len = a.length, count = 0;
        int i, j, temp;
        while (h < len/3)
        {
            h = h * 3 + 1;
        }
        while (h >= 1)
        {
            //增量为h的插入排序
            for (i = h; i < len; i ++)
            {
                temp = a[i];
                j = i - h;
                while ( j >= 0 && a[j] > temp)
                {
                    a[j + h] = a[j];
                    j -= h;
                    count ++;
                }
                a[j + h] = temp;
            }
            h = h / 3;
        }
        System.out.println("count = " + count);
    }
}
