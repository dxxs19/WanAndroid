package com.wei.wanandroid.javasources.designpatterns.flyweight;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TicketFactory {
    private static Map<String, Ticket> stringTicketMap = new ConcurrentHashMap<>();

    public static Ticket getTicket(String from, String to)
    {
        String key = from + "-" + to;
        if (stringTicketMap.containsKey(key))
        {
            System.out.println("使用缓存 ==> " + key);
            return stringTicketMap.get(key);
        }else{
            System.out.println("创建对象 ==> " + key);
            Ticket ticket = new TrainTicket(from, to);
            stringTicketMap.put(key, ticket);
            return ticket;
        }
    }

}
