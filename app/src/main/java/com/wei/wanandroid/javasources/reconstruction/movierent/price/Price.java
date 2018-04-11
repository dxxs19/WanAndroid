package com.wei.wanandroid.javasources.reconstruction.movierent.price;


/**
 * @author: WEI
 * @date: 2018/4/11
 */
public abstract class Price {
    public abstract int getPriceCode();

    public abstract double getCharge(int daysRented);

    /**
     * 计算积分。默认返回1，新片则需要重写该方法，其它片不需要重写
     * @param daysRented
     * @return
     */
    public int getFrequentRenterPoints(int daysRented) {
        return 1;
    }
}
