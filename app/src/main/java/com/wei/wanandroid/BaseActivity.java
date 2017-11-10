package com.wei.wanandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.wei.utillibrary.ToastUtils;
import com.wei.wanandroid.http.IRequestCallBacked;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
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
}
