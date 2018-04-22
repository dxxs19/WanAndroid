package com.wei.wanandroid.javasources.designpatterns.facade;

/**
 * 使用方式类似于通过Context可以调用startActivity，getResource等等。
 * 假设手机包含相机和打电话两个功能。MobilePhone把所有功能都封装起来，为用户提供一个统一的操作接口，
 * 也就是说用户只需要通过MobilePhone这个类就可以操作打电话和拍照。至于功能是怎么实现的，用户不必关心。
 */
public class MobilePhone {
     Phone phone = new PhoneImpl();
     Camera camera = new XiaomiCamera();

     public void videoChat()
     {
         camera.open();
         phone.call();
     }

     public void call()
     {
         phone.call();
     }

     public void hangup()
     {
         phone.hangup();
     }

     public void takePicture()
     {
         camera.open();
         camera.takePicture();
     }

     public void closeCamera()
     {
         camera.close();
     }
}
