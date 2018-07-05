package com.wei.utillibrary;

/**
 * 系统相关工具类
 * @author: WEI
 * @date: 2018/6/26
 */
public class OSUtil
{
    /**
     * 获取app可分配内存大小(M)
     * 小米5:256M；设置 android:largeHeap="true" 后，增大一倍，为 512M
     * @return
     */
    public static long getMaxMemory()
    {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        return maxMemory/(1024*1024);
    }
}
