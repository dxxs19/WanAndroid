package com.wei.wanandroid.javasources.designpatterns.adapter.objectadapter;

import com.wei.wanandroid.javasources.designpatterns.adapter.classadapter.FiveVolt;
import com.wei.wanandroid.javasources.designpatterns.adapter.classadapter.Volt220;

/**
 * @author: WEI
 * @date: 2018/4/17
 */
public class VoltAdapter implements FiveVolt {
    Volt220 mVolt220;

    public VoltAdapter(Volt220 volt220)
    {
        mVolt220 = volt220;
    }

    public int getVolt220()
    {
        return mVolt220.getVolt220();
    }

    @Override
    public int getVolt5() {
        return 5;
    }
}
