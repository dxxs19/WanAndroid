package com.wei.wanandroid.javasources.designpatterns.adapter.classadapter;

/**
 * Adapter 角色，将220V 的电压转换成 5V 的电压
 * @author: WEI
 * @date: 2018/4/17
 */
public class VoltAdapter extends Volt220 implements FiveVolt {
    @Override
    public int getVolt5() {
        return 5;
    }
}
