package com.wei.wanandroid.activity.dagger_android;

import android.os.Bundle;
import android.util.Log;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.bean.BeautyBean;
import com.wei.wanandroid.bean.EventMessage;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DaggerAndroidActivity extends BaseActivity
{
//    @Inject
//    String mClassName;
//    @Inject
//    EventMessage mEventMessage;
//    @Inject
//    BeautyBean mBeautyBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AndroidInjection.inject(this);
        setContentView(R.layout.activity_dagger_android);
    }

    @Override
    public void initView() {
//        Log.e(TAG, mClassName + ", " + mEventMessage.toString() + ", " + mBeautyBean.toString());

    }
}
