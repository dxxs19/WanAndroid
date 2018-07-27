package com.wei.wanandroid.activity;

import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.wei.utillibrary.ToastUtils;
import com.wei.wanandroid.R;
import com.wei.wanandroid.widgets.CustomProgressDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Administrator
 */
public abstract class BaseActivity extends AppCompatActivity
{
    protected String TAG = getClass().getSimpleName();
    protected static Handler sHandler = new Handler();
    protected CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    public abstract void initView();


    protected void showMsg(String msg, int period)
    {
        ToastUtils.showToast(this, msg, period);
    }

    /**
     * showBindTalkbackFailDialog
     * 显示一个加载中的loading对话框progressDialog
     * 默认能手动取消
     * @param message
     */
    public void showProgress(String message)
    {
        showProgressDialog(message, true);
    }

    /**
     * 取消方式可设置
     * @param message
     * @param cancelable
     */
    public void showProgress(String message, boolean cancelable)
    {
        showProgressDialog(message, cancelable);
    }

    private void showProgressDialog(String message, boolean cancelable)
    {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new CustomProgressDialog(this, R.style.Dialog, message);
        progressDialog.setCancelable(cancelable);
        try {
            progressDialog.show();
        } catch (Exception e) {
            //网页JavaScript调用本接口时，该Activity有可能已经被关闭，所以要捕捉此异常
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 关闭progressDialog
     */
    @UiThread
    public void hidProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
