package com.wei.wanandroid.javasources.operation;

/**
 * @author: WEI
 * @date: 2017/11/30
 */

public class MathTest
{
    int i = 1;
    long l = i; // 弱转强，无须强制转换；
    int ii = (int) l; // 强转弱，需要强制转换

    double d = 1.3;
    float f = 1.3f; // 默认是double类型，需要在后面加f; 强转弱，需要强制转换，或者在后面加f
    double dd = f; // 弱转强，无须强制转换；

    char c = 'a';
    byte b = -128; // byte范围：-128 至 127

    Boolean bb = null; // 编译不会出错

    public static void main(String[] args)
    {
        long round = Math.round(11.5);   // 四舍五入
        double ceil = Math.ceil(-11.5); // 大于它的最小整数
        double floor = Math.floor(-11.5);  // 小于它的最大整数
        System.out.println( round + ", " + ceil + ", " + floor); // 12, -11.0, -12.0
    }
}
