package com.wei.wanandroid.activity.dagger_android;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * @author: WEI
 * @date: 2018/7/30
 */
@Module(subcomponents = DaggerAndroidActivitySubcomponent.class)
public abstract class DaggerAndroidModule {
    @Binds
    @IntoMap
    @ActivityKey(DaggerAndroidActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindDaggerAndroidActivityInjectorFactory(DaggerAndroidActivitySubcomponent.Builder builder);
}
