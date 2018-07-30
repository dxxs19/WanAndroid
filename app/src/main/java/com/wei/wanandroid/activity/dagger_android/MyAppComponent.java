package com.wei.wanandroid.activity.dagger_android;

import com.wei.wanandroid.WanApplication;
import com.wei.wanandroid.bean.BeautyBean;
import com.wei.wanandroid.bean.EventMessage;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author: WEI
 * @date: 2018/7/30
 */
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        EventMessage.class,
        BeautyBean.class
})
public interface MyAppComponent
{
    void inject(WanApplication application);
}
