package com.wei.utillibrary;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * @author: WEI
 * @date: 2017/11/6
 */

public class ToastUtils
{
    public static void showToast(final Activity context, final String msg, final int period)
    {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, ( period <= 0 ? Toast.LENGTH_SHORT : period ) ).show();
            }
        });
    }
}
