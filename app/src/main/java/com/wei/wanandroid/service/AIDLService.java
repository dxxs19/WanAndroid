package com.wei.wanandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.wei.wanandroid.ICalcAIDL;

/**
 * 被其它进程调用，相当于服务端
 */
public class AIDLService extends Service
{
    private final String TAG = getClass().getSimpleName();

    public AIDLService() {
    }

    private IBinder mBinder = new ICalcAIDL.Stub() {
        @Override
        public int add(int x, int y) throws RemoteException {
            Log.e(TAG, "收到远程请求执行加法 : " + x + " + " + y );
            return x + y;
        }

        @Override
        public int min(int x, int y) throws RemoteException {
            Log.e(TAG, "收到远程请求执行减法 : " + x + " - " + y );
            return x - y;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
