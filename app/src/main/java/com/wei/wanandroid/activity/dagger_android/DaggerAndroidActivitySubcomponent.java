package com.wei.wanandroid.activity.dagger_android;

import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * @author: WEI
 * @date: 2018/7/30
 */
@Subcomponent(modules = {

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

    }

}
