package com.wei.wanandroid.activity.keeplive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.wei.utillibrary.LogUtil;

/**
 * @author: WEI
 * @date: 2018/5/21
 */
public class KeepLiveManager
{
    private static ScreenStateReceiver sScreenStateReceiver;

    public static void registerBroadCast(Context context)
    {
        sScreenStateReceiver = new ScreenStateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        // 亮屏时触发
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        // 锁屏时触发
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        // 解锁成功后触发
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(sScreenStateReceiver, intentFilter);
    }

    public static void unRegisterBroadCast(Context context)
    {
        if (sScreenStateReceiver != null)
        {
            context.unregisterReceiver(sScreenStateReceiver);
        }
    }

    public static class ScreenStateReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            LogUtil.e("KeepLiveManager", intent.getAction());
            if ( Intent.ACTION_SCREEN_ON.equals( intent.getAction() ) || Intent.ACTION_USER_PRESENT.equals( intent.getAction() ) )
            { // 亮屏或者解锁时对一像素界面进行销毁
                KeepliveActivity.destroyOnePixelActivity();
            }
            else if ( Intent.ACTION_SCREEN_OFF.equals( intent.getAction() ) )
            { // 锁屏时启动一像素界面，此时进程的oom_adj=0，为android进程中最高优先级，对应前台进程，通常不会被杀
                KeepliveActivity.showOnePixelActivity();
            }
        }
    }
}
