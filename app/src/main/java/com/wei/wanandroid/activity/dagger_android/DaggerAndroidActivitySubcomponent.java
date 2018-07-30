package com.wei.wanandroid.activity.dagger_android;

import com.wei.wanandroid.bean.BeautyBean;
import com.wei.wanandroid.bean.EventMessage;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * @author: WEI
 * @date: 2018/7/30
 */
@Subcomponent(modules = {
        AndroidInjectionModule.class,
        DaggerAndroidActivitySubcomponent.SubModule.class
})
public interface DaggerAndroidActivitySubcomponent extends AndroidInjector<DaggerAndroidActivity>
{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerAndroidActivity>
    {

    }

    @Module
    class SubModule
    {
        @Provides
        String provideClassName()
        {
            return DaggerAndroidActivity.class.getName();
        }

        @Provides
        EventMessage provideEventMessage()
        {
            return new EventMessage("事件实体");
        }

        @Provides
        BeautyBean provideBeautyBean()
        {
            return new BeautyBean("/DCIM/Screenshots/beauty.jpg", "beauty.jpg");
        }



    }

}
