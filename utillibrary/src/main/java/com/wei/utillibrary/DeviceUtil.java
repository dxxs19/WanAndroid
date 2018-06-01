package com.wei.utillibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * author: WEI
 * date: 2017/8/17
 */

public class DeviceUtil {
    /**
     * 获取设备唯一ID
     * @param mContext
     * @return
     */
    public static String getDeviceId(Context mContext) {
        String uniqueCode = Build.SERIAL;
        if (TextUtils.isEmpty(uniqueCode)) {
            uniqueCode = getMacAddress(mContext);
            if (TextUtils.isEmpty(uniqueCode)) {
                if (Build.VERSION.SDK_INT < 23) {
                    uniqueCode = getIMEI(mContext);
                } else {
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED) {
                        uniqueCode = getIMEI(mContext);
                    }
                }
            }
        }
        return uniqueCode;
    }

    /**
     * 获取设备唯一标识码
     * @param mContext
     * @return
     */
    public static String generateUniqueCode(Context mContext) {
        String md5Id = MD5Utils.MD5Encode(getDeviceId(mContext));
        return md5Id.replaceFirst(md5Id.substring(0, 1), "_");  // 要求以"_"开头
    }

    /**
     * 获取mac地址
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        String macAddress = null;
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName("wlan0");
            if (null == networkInterface) {
                return "";
            }
            byte[] macs = networkInterface.getHardwareAddress();
            if (null == macs) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            for (byte b : macs) {
                builder.append(String.format("%02x:", b));
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            macAddress = builder.toString();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return macAddress;
    }

    /**
     * 获取IMEI号
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        String imes = telephonyManager.getDeviceId();
        return imes;
    }

    /**
     * 隐藏软键盘(无输入框或者说无法获取输入框。比如，微信支付时处于未登录状态，此时输入框是微信的，返回再隐藏键盘)
     * @param context
     */
    public static void hideSoftKeyboard(@NonNull Activity context)
    {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘(有输入框)
     * @param context
     * @param mEditText
     */
    public static void hideSoftKeyboard(@NonNull Context context, @NonNull EditText mEditText)
    {
        InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
