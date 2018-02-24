package com.wei.wanandroid.javasources.datatype;

/**
 * String 类是不可改变的，即一旦一个String对象被创建以后，包含在这个对象中的字符序列是不可改变的，直到这个对象被销毁
 * @author: WEI
 * @date: 2018/2/23
 */

public class StringChange
{
    String str = new String("good"); // 重新赋值后 hascode 变了
    char[] ch = {'a', 'b', 'c'};  // 重新赋值后 hascode 不变
    StringBuilder mBuilder = new StringBuilder(); // 重新赋值后 hascode 不变

    public static void main(String[] args) {
        StringChange change = new StringChange();
        change.change(change.str, change.ch);
        change.mBuilder.append(change.str);

        System.out.println(change.str.hashCode() + ", " + change.ch.hashCode() + ", " + change.mBuilder.hashCode());
        System.out.print(change.str + " and ");
        System.out.println(change.ch);

        change.str += "abc";
        change.mBuilder.append(change.str);

        System.out.println(change.str.hashCode() + ", " + change.mBuilder.hashCode());
        /**
         -1422516182, 1639705018
         3178685, 1639705018, 1627674070
         good and gbc
         207020677, 1627674070
         */
    }

    public void change(String str, char[] ch)
    {
        str = "test ok";
        // 一旦赋值，则str的hascode就会改变。此时的str已不再是传进来时的str。如果不赋值，则str的hascode不改变，依然是传进来时的str
        System.out.println(str.hashCode() + ", " + ch.hashCode());
        ch[0] = 'g';
    }
}
