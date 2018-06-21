package com.wei.qrcodescanner;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 动态权限申请控制类
 * Created by WEI on 2016/12/19.
 */

public class PermissionController
{
    /**
     * 请求权限
     * @param context
     * @param permissions
     * @param requestCode
     */
    public static void requestPermissions(Context context, String[] permissions, int requestCode)
    {
        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
    }

    /**
     * 相应权限是否已授权
     * @param context
     * @param targetPermission
     * @return
     */
    public static boolean grantedPermission(Context context, String targetPermission)
    {
        int permission = ContextCompat.checkSelfPermission(context, targetPermission);
        return permission == PackageManager.PERMISSION_GRANTED;
    }
}
