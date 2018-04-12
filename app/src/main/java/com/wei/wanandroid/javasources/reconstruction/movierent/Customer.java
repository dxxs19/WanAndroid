package com.wei.wanandroid.javasources.reconstruction.movierent;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 顾客类
 *
 * @author: WEI
 * @date: 2018/4/11
 */
public class Customer {
    private String name;
    private Vector rentals = new Vector();

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRental(Rental rental) {
        rentals.addElement(rental);
    }

    public static void main(String[] args) {
        Movie movie = new Movie("《环太平洋2》", Movie.NEW_RELEASE);
        Movie movie2 = new Movie("《前任3》", Movie.REGULAR);
        Movie movie3 = new Movie("《复仇者联盟2》", Movie.REGULAR);
        Movie movie4 = new Movie("《变形记》", Movie.CHILDRENS);

        Rental rental = new Rental(movie, 10);
        Rental rental2 = new Rental(movie2, 5);
        Rental rental3 = new Rental(movie3, 4);
        Rental rental4 = new Rental(movie4, 6);

        Customer customer = new Customer("小明");
        customer.addRental(rental);
        customer.addRental(rental2);

        Customer customer1 = new Customer("大乔");
        customer1.addRental(rental3);
        customer1.addRental(rental4);

        String result1 = customer.statement();
        String result2 = customer1.statement();
        System.out.println(result1);
        System.out.println();
        System.out.println(result2);
    }

    /**
     * 清单
     * @return
     */
    public String statement() {
        // Enumeration 接口是Iterator迭代器的“古老版本”
        Enumeration elements = rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (elements.hasMoreElements()) {
            Rental each = (Rental) elements.nextElement();
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(each.getCharge()) + "\n";
        }
        result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
        result += "You earned " + String.valueOf(getTotalFrequentRenterPoints()) + " frequent renter points";
        return result;
    }

    /**
     * 计算积分
     * @return
     */
    private int getTotalFrequentRenterPoints()
    {
        int result = 0;
        Enumeration elements = rentals.elements();
        while (elements.hasMoreElements()) {
            Rental each = (Rental) elements.nextElement();
            result += each.getFrequentRenterPoints();
        }
        return result;
    }

    /**
     * 计算总费用
     * @return
     */
    private double getTotalCharge()
    {
        double result = 0;
        Enumeration elements = rentals.elements();
        while (elements.hasMoreElements()) {
            Rental each = (Rental) elements.nextElement();
            result += each.getCharge();
        }
        return result;
    }
}
