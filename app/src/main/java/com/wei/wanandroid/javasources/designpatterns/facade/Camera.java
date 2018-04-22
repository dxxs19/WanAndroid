package com.wei.wanandroid.javasources.designpatterns.facade;

public interface Camera {
    /**
     * 打开相机
     */
    void open();

    /**
     * 拍照
     */
    void takePicture();

    /**
     * 关闭相机
     */
    void close();
}
