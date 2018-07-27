package com.wei.wanandroid.javasources.basics;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: WEI
 * @date: 2018/4/24
 */
public class TestString
{
    private Object sNull;
    private static String sString = "I love you!";
    private static String[] sStrings = {sString, "am"};

    public static void main(String[] args)
    {
        System.out.println("".equals(null));
        new TestString().changeStr(sString);
        System.out.println(sString);
        System.out.println(sString == (sString+""));
        System.out.println(sString.equals(sString+""));
        new TestString().changeStr(sStrings);
        System.out.println(sStrings[0]);

        Map map = new HashMap(1);
        // 如果key相同，则后面的会覆盖前面的
        map.put(null, null);
        map.put(null, 1);
        map.put("key", "value");
        map.put("x", "wei");
        map.put("x", "w");

        System.out.println(map.get(null));
        System.out.println(map.get("x"));

    }

    private void changeStr(String[] strings) {
        strings[0] = "I o u";
    }

    private void changeStr(String content) {
        content = "I hate you !";
    }
}
