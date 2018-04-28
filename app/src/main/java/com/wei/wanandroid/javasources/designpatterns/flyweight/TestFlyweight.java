package com.wei.wanandroid.javasources.designpatterns.flyweight;

/**
 * 享元模式
 */
public class TestFlyweight {
    public static void main(String[] args) {
        Ticket ticket = TicketFactory.getTicket("茂名", "广州");
        ticket.showTicketInfo("上铺");
        Ticket ticket1 = TicketFactory.getTicket("茂名", "广州");
        ticket1.showTicketInfo("下铺");
        Ticket ticket2 = TicketFactory.getTicket("广州", "茂名");
        ticket2.showTicketInfo("硬座");
    }
}
