package com.wei.wanandroid.javasources.basics;

/**
 * ==可用于基本数据类型及引用类型之间比较，在栈中比较。如果基本数据类型的值相等，则认为两个基本类型
 * 是相等的；如果引用的对象是同一个，即对象引用（地址）相同，则认为两个变量相等，否则都不相等；
 * equals通常用于对象之间的比较，在堆中比较。可自定义equals方法，通常内容相等则认为对象相等。
 * @author: WEI
 * @date: 2018/4/10
 */
public class EqualsHashCode {
    public static void main(String[] args) {
        new EqualsHashCode().testEquals();
    }

    /**
     * 判断是否相等
     */
    private void testEquals() {
        A a = new A();
        A aa = new A();
        // false。 栈中存放的是对象的引用（地址）。由此可见'=='是对栈中的值进行比较的。很明显a和aa指向的引用地址不一样，所以不相等。
        System.out.println("a == aa ? " + ( a == aa ) );
        aa = a;
        // true。 此时 a 和 aa 指向同一个对象。
        System.out.println("a == aa ? " + ( a == aa ) );
        A aaa = new A();
        // false。 未重写equals方法时，比较的是它们是否是同一个对象(调用的是父类Object的equals方法)。此时equals等效于==。
        System.out.println("a.equals(aaa) ? " + (a.equals(aaa)));

        System.out.println("a.hashCode = " + a.hashCode() + ", aaa.hashCode = " + aaa.hashCode());
        int x = 10;
        float y = 10.0f;
        System.out.println("x == y ? " + (x == y));
    }
}

class A
{
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
//        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
//        return 1;
    }
}

