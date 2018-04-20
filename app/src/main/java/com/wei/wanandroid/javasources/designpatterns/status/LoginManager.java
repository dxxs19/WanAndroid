package com.wei.wanandroid.javasources.designpatterns.status;

import android.content.Context;

/**
 * @author: WEI
 * @date: 2018/4/19
 */
public class LoginManager implements IUserStatus
{
    /** 默认是未登录状态 **/
    IUserStatus mStatus = new LogoutStatus();

    public void setUserStatus(IUserStatus userStatus)
    {
        mStatus = userStatus;
    }

    /** 分享 **/
    @Override
    public void share(Context context)
    {
        mStatus.share(context);
    }

    /** 转发 **/
    @Override
    public void transpond(Context context)
    {
        mStatus.transpond(context);
    }

    /** 评论 **/
    @Override
    public void comment(Context context)
    {
        mStatus.comment(context);
    }
}
