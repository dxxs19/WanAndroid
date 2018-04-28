package com.wei.wanandroid.javasources.designpatterns.facade;

/**
 * 外观模式
 */
public class TestFacade {
    public static void main(String[] args) {
        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone.videoChat();
        System.out.println("=======================");
        mobilePhone.takePicture();
    }
}
