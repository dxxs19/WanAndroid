package com.wei.wanandroid.javasources.designpatterns.facade;

public class TestFacade {
    public static void main(String[] args) {
        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone.videoChat();
        System.out.println("=======================");
        mobilePhone.takePicture();
    }
}
