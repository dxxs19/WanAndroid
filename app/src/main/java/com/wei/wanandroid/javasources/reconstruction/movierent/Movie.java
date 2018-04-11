package com.wei.wanandroid.javasources.reconstruction.movierent;

import com.wei.wanandroid.javasources.reconstruction.movierent.price.ChildrensPrice;
import com.wei.wanandroid.javasources.reconstruction.movierent.price.NewReleasePrice;
import com.wei.wanandroid.javasources.reconstruction.movierent.price.Price;
import com.wei.wanandroid.javasources.reconstruction.movierent.price.RegularPrice;

/**
 * 电影类，分三种类型
 *
 * @author: WEI
 * @date: 2018/4/11
 */
public class Movie {
    /**
     * 儿童片
     */
    public static final int CHILDRENS = 2;

    /**
     * 普通片
     */
    public static final int REGULAR = 0;

    /**
     * 新片
     */
    public static final int NEW_RELEASE = 1;

    private String title;
    private int priceCode;
    private Price mPrice;

    public Movie(String title, int priceCode) {
        this.title = title;
//        this.priceCode = priceCode;
        setPriceCode(priceCode);
    }

    /**
     * 计算费用
     *
     * @param daysRented
     * @return
     */
    public double getCharge(int daysRented) {
        return mPrice.getCharge(daysRented);
    }

    /**
     * 计算积分
     *
     * @param daysRented
     * @return
     */
    public int getFrequentRenterPoints(int daysRented) {
        return mPrice.getFrequentRenterPoints(daysRented);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriceCode() {
        return mPrice.getPriceCode();
    }

    public void setPriceCode(int priceCode) {
//        this.priceCode = priceCode;
        switch (priceCode) {
            case CHILDRENS:
                mPrice = new ChildrensPrice();
                break;

            case NEW_RELEASE:
                mPrice = new NewReleasePrice();
                break;

            case REGULAR:
                mPrice = new RegularPrice();
                break;

            default:
        }
    }
}
