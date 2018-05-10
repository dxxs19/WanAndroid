package com.wei.wanandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.sophix.SophixManager;
import com.wei.utillibrary.ToastUtils;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.http.IRequestCallBacked;
import com.wei.wanandroid.widgets.CustomProgressDialog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Administrator
 */
public abstract class BaseActivity extends AppCompatActivity implements IRequestCallBacked
{
    protected String TAG = getClass().getSimpleName();
    private OkHttpClient mOkHttpClient;
    protected static Handler sHandler = new Handler();
    protected CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initView();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
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
        SophixManager.getInstance().queryAndLoadNewPatch();
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
     * 根据url返回组装后的builder
     * @param url
     * @return
     */
    protected Request.Builder getBuilder(String url)
    {
        return new Request.Builder().url(url);
    }

    /**
     * 网络请求
     * @param request
     */
    protected void requestCall(Request request)
    {
        File sdcache = getExternalCacheDir();
        Log.e(TAG, "cache path : " + sdcache.getAbsolutePath());
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                BaseActivity.this.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BaseActivity.this.onResponse(call, response);
            }
        });
    }

    @Override
    public void onFailure(Call call, IOException e) {
        mRequestCallBack.onFailure(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        mRequestCallBack.onResponse(call, response);
    }

    private RequestCallBack mRequestCallBack;
    public void setRequestCallBack(RequestCallBack requestCallBack) {
        mRequestCallBack = requestCallBack;
    }
    public interface RequestCallBack
    {
        void onFailure(Call call, IOException e);
        void onResponse(Call call, Response response) throws IOException;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "--- dispatchTouchEvent ---");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "--- onTouchEvent ---");
        return super.onTouchEvent(event);
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
