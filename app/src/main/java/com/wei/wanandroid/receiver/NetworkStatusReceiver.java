package com.wei.wanandroid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.wei.utillibrary.FileUtil;
import com.wei.wanandroid.activity.MainActivity;
import com.wei.wanandroid.activity.http.OkHttp3Activity;

public class NetworkStatusReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(TAG, "action : " + intent.getAction());
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo hasNetWork = conManager.getActiveNetworkInfo();
        if (hasNetWork != null)
        {
            if (hasNetWork.isAvailable())
            {
                // 有网络连接并且可用
                Log.d(TAG, "当前网络可用！");
            }
            else
            {
                String tips = "当前网络不可用，请切换网络再重试！";
                Toast.makeText(context, tips, Toast.LENGTH_LONG).show();
                Log.d(TAG, tips);
                Intent intent1 = new Intent(context, OkHttp3Activity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
                FileUtil.saveFile(tips);
            }
        }
        else
        {
            Toast.makeText(context, "当前无网络连接，请连接网络再重试！", Toast.LENGTH_LONG).show();
            Log.d(TAG, "当前无网络连接，请连接网络再重试！");
//            Intent intent1 = new Intent(context, OkHttp3Activity.class);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent1);
        }

    }
}
