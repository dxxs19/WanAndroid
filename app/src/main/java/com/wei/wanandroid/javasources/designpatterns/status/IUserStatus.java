package com.wei.wanandroid.javasources.designpatterns.status;

import android.content.Context;

/**
 * 用户状态
 * @author: WEI
 * @date: 2018/4/19
 */
public interface IUserStatus {
    /** 分享 **/
    void share(Context context);

    /** 转发 **/
    void transpond(Context context);

    /** 评论 **/
    void comment(Context context);
}
