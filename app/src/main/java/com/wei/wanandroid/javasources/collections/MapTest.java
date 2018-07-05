package com.wei.wanandroid.javasources.collections;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author: WEI
 * @date: 2018/5/4
 */
public class MapTest
{
    public static void main(String[] args) {
        Map map = new HashMap(3);
        map.put(110, "110");
        map.put(119, 119);
        map.put(110, "iou!");
        map.put(111, 111);
        Gson gson = new Gson();
        String jsonMap = gson.toJson(map);
        Map map1 = gson.fromJson(jsonMap, Map.class);
        Double doubleIndex = (Double) map1.get("111");
        int index = doubleIndex.intValue();
        System.out.println(map.get(110) + ", " + map.get(119) + ", " + jsonMap + ", " + index);
        Vector<Integer> integerVector = new Vector<>();
        for (int i = 0 ; i < 100; i ++)
        {
//            integerVector.addElement(i);
            integerVector.add(i);
        }

        for (int i = 0;i < integerVector.size(); i ++)
        {
            System.out.print(integerVector.get(i) + ", ");
        }
    }
}
