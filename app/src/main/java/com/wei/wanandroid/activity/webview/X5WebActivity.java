package com.wei.wanandroid.activity.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class X5WebActivity extends BaseActivity
{
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";
    X5WebView mX5WebView;
    private long mStartTime;
    private String mTitle, mUrl;

    public static void startWebActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, X5WebActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mStartTime = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        mTitle = getIntent().getStringExtra(KEY_TITLE);
        mUrl = getIntent().getStringExtra(KEY_URL);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_x5_web);
    }

    @Override
    public void initView() {
        mX5WebView = findViewById(R.id.x5_webview);
        mX5WebView.loadUrl(mUrl);
        mX5WebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                long loadTime = System.currentTimeMillis() - mStartTime;
                Log.e(TAG, "加载该页面耗时 ：" + loadTime + " ms");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
