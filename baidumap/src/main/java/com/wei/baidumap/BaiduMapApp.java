package com.wei.baidumap;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

public class BaiduMapApp extends Application
{
    public static BaiduMapApp sBaiduMapApp;
    private LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        sBaiduMapApp = this;
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
    }

    public LocationService getLocationService() {
        return locationService;
    }
}
