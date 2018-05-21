package com.wei.utillibrary;

import android.util.Log;


public class LogUtil {
    private static final boolean ISDEBUG = true;

    public static void e(String tag, String msg)
    {
        if (ISDEBUG)
        {
            Log.e(tag, msg);
        }
    }
}
