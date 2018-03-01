// ICalcAIDL.aidl
package com.wei.wanandroid;

// Declare any non-default types here with import statements
// 要放到其它工程下运行时，aidl包下面的东西都要原封不动地复制到其它工程的同样路径
interface ICalcAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    //在aidl文件写要被调用的方法,此方法不能有权限修饰符
    int add(int x, int y);
    int min(int x, int y);
}
