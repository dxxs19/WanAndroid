package com.wei.wanandroid.javasources.reconstruction.movierent.original;

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
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        // Enumeration 接口是Iterator迭代器的“古老版本”
        Enumeration elements = rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (elements.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) elements.nextElement();
            thisAmount = amountFor(each);
            switch (each.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (each.getDaysRented() > 2)
                    {
                        thisAmount += (each.getDaysRented() - 2) * 1.5;
                    }
                    break;

                case Movie.CHILDRENS:
                    thisAmount += each.getDaysRented() * 3;
                    break;

                case Movie.NEW_RELEASE:
                    thisAmount += 1.5;
                    if (each.getDaysRented() > 3)
                    {
                        thisAmount += (each.getDaysRented() - 3) * 1.5;
                    }
                    break;

                default:
            }

            frequentRenterPoints ++;
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1)
            {
                frequentRenterPoints ++;
            }
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }

    private double amountFor(Rental each) {
        return 0;
    }
}
