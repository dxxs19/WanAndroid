package com.wei.wanandroid.javasources.reconstruction.movierent.price;

import com.wei.wanandroid.javasources.reconstruction.movierent.Movie;

/**
 * @author: WEI
 * @date: 2018/4/11
 */
public class ChildrensPrice extends Price {
    @Override
    public int getPriceCode() {
        return Movie.CHILDRENS;
    }

    @Override
    public double getCharge(int daysRented) {
        return daysRented * 3;
    }
}
