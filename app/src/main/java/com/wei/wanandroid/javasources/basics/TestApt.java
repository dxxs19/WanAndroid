package com.wei.wanandroid.javasources.basics;

import com.songwenju.aptproject.HelloWorld;
import com.wei.processorlib.AutoCreat;
import com.wei.processorlib.IsNull;
import com.wei.wanandroid.isnullvalidate.STRINGIsNullValidate;

/**
 * @author: WEI
 * @date: 2018/8/2
 */
@AutoCreat
public class TestApt
{
    @IsNull
    public static final String STRING = "";

    public static void main(String[] args) {
        String[] strings = new String[]{"a", "b"};
        HelloWorld.main(strings);
        System.out.println(STRINGIsNullValidate.isNull());
    }
}
