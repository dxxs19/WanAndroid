package com.wei.wanandroid.javasources.designpatterns.status;

import android.content.Context;

/**
 * 登录状态，可直接进行分享、转发及评论操作
 * @author: WEI
 * @date: 2018/4/19
 */
public class LoginStatus implements IUserStatus {
    @Override
    public void share(Context context) {
        printMsg("分享成功");
    }

    @Override
    public void transpond(Context context) {
        printMsg("转发成功");
    }

    @Override
    public void comment(Context context) {
        printMsg("评论成功");
    }

    private void printMsg(String msg)
    {
        System.out.println(msg);
    }
}
