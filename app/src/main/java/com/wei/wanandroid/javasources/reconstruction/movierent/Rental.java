package com.wei.wanandroid.javasources.reconstruction.movierent;

/**
 * 租赁类，包含租赁的电影类型及租赁天数
 * @author: WEI
 * @date: 2018/4/11
 */
public class Rental {
    private Movie mMovie;
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        mMovie = movie;
        this.daysRented = daysRented;
    }

    /**
     * 计算费用
     * @return
     */
    public double getCharge( )
    {
        return mMovie.getCharge(daysRented);
    }

    /**
     * 计算积分
     * @return
     */
    public int getFrequentRenterPoints() {
        return mMovie.getFrequentRenterPoints(daysRented);
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }

}
