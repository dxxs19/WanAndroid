package com.wei.wanandroid.javasources.basics;

import java.util.Arrays;

/**
 * @author: WEI
 * @date: 2018/4/26
 */
public class TestArray
{
    public static void main(String[] args) {
        TestArray testArray = new TestArray();
        testArray.testArrayCopy();
    }

    private void testArrayCopy() {
        String[] array1 = {"abc", "231", "beau"};
        String[] array2 = {"xyz", "iou", "tyleg", "concat", "pig"};
        // 此时array2为： abc, 231, beau, concat, pig 。 即array1的内容被复制到array2数组的前面并把array2相应的内容替换了。
//        System.arraycopy(array1, 0, array2, 0, array1.length);
        // 输出：abc, 231, beau, null, null, null 。 底层调用的是：System.arraycopy(original, 0, copy, 0,Math.min(original.length, newLength));然后返回copy数组。
        // copy 数组容量为 newLength。集合类扩容时经常会使用Arrays.copyOf
        String[] copyArray = Arrays.copyOf(array1, array1.length + 3);
        printArray("array1", array1);
        printArray("array2", array2);
        printArray("copyArray", copyArray);
        System.out.println("copyArray.length = " + copyArray.length);
    }

    private void printArray(String name, String[] array)
    {
        int len = array.length;
        System.out.print(name + " : ");
        for (int i = 0; i < len; i ++)
        {
            System.out.print(array[i]);
            if (i < len-1)
            {
                System.out.print(", ");
            }
            else
            {
                System.out.println();
            }
        }
    }
}
