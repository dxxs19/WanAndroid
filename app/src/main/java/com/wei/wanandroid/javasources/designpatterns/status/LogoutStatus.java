package com.wei.wanandroid.javasources.designpatterns.status;

import android.content.Context;

/**
 * 退出/注销状态，此时不论是点击分享、转发还是评论，都要先跳转到登录界面，登录成功后才可以进行前面的操作。
 * @author: WEI
 * @date: 2018/4/19
 */
public class LogoutStatus implements IUserStatus {
    @Override
    public void share(Context context) {
        gotoLoginActivity(context);
    }

    @Override
    public void transpond(Context context) {
        gotoLoginActivity(context);
    }

    @Override
    public void comment(Context context) {
        gotoLoginActivity(context);
    }

    private void gotoLoginActivity(Context context) {
        System.out.println("正跳转到登录界面进行登录......");
    }
}
