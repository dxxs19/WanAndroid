package com.wei.wanandroid.javasources.reconstruction.movierent.price;

import com.wei.wanandroid.javasources.reconstruction.movierent.Movie;

/**
 * @author: WEI
 * @date: 2018/4/11
 */
public class RegularPrice extends Price {
    @Override
    public int getPriceCode() {
        return Movie.REGULAR;
    }

    @Override
    public double getCharge(int daysRented) {
        double result = 2;
        if (daysRented > 2) {
            result += (daysRented - 2) * 1.5;
        }
        return result;
    }

}
