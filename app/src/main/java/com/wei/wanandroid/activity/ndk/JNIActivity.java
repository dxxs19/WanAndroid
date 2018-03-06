package com.wei.wanandroid.activity.ndk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

public class JNIActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
    }

    @Override
    public void initView() {
        String code = getAppID();
        Log.e(TAG, "code : " + code);
    }

    static {
        System.loadLibrary("jniTest");
    }

    private native String getAppID();
    private void invokedByNative(String msg)
    {
        Log.e(TAG, "底层回调信息 ： " + msg);
    }
}
