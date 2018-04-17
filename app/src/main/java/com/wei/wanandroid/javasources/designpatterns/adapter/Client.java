package com.wei.wanandroid.javasources.designpatterns.adapter;

import com.wei.wanandroid.javasources.designpatterns.adapter.classadapter.FiveVolt;
import com.wei.wanandroid.javasources.designpatterns.adapter.classadapter.Volt220;
import com.wei.wanandroid.javasources.designpatterns.adapter.classadapter.VoltAdapter;

/**
 * 适配器模式把一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作。
 * @author: WEI
 * @date: 2018/4/17
 */
public class Client {
    public static void main(String[] args) {
        // 类适配器是通过实现 Target 接口以及继承 Adaptee 类来实现接口转换。
        FiveVolt voltAdapter = new VoltAdapter();
//        System.out.println("需要转换对象的电压是：" + voltAdapter.getVolt220());
        System.out.println("转换后的电压是：" + voltAdapter.getVolt5());

        com.wei.wanandroid.javasources.designpatterns.adapter.objectadapter.VoltAdapter adapter =
                new com.wei.wanandroid.javasources.designpatterns.adapter.objectadapter.VoltAdapter(new Volt220());
        System.out.println("需要转换的电压：" + adapter.getVolt220());
        System.out.println("转换后的电压： " + adapter.getVolt5());
    }
}
