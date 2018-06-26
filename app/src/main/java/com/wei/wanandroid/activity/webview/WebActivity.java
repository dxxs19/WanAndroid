package com.wei.wanandroid.activity.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

import java.util.Stack;

public class WebActivity extends BaseActivity {

    private final static String TAG = WebActivity.class.getSimpleName();
    private boolean mLoadError;
    /**
     * webview 加载链接出错时显示的H5页面
     **/
    public static final String ERROR_URL = "file:///android_asset/html5/loading/notFount.htm";
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";

    /**
     * url跳转记录，用来做页面内回退
     */
    Stack<String> historyStack = new Stack<String>();

    WebView webView, errorWebView;

    /**
     * 当前页面的url，用来刷新页面
     */
    private String currentUrl;

    /**
     * 界面标题
     */
    public String title;

    /**
     * 跳转到webactivity
     *
     * @param title
     * @param url
     * @auther WEI
     */
    public static void startWebActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WebActivity.KEY_TITLE, title);
        intent.putExtra(WebActivity.KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra(KEY_TITLE);
        currentUrl = getIntent().getStringExtra(KEY_URL);
        setContentView(R.layout.activity_web);
    }

    @Override
    public void initView() {
        webView = findViewById(R.id.webView_success);
        errorWebView = findViewById(R.id.webView_error);
        initWebView();
    }

    /**
     * android 调用 js 方法，并且可接收返回值。比loadUrl功能强大
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void testEvaluateJavascript()
    {
        webView.evaluateJavascript("getContent('I miss yout!', 10000)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.e(TAG, "--- evaluateJavascript --- js 返回值 ： " + value);
                showMsg(value, Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        historyStack.clear();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        if (!TextUtils.isEmpty(title)) {
//            setCustomTitle(title);
        }
        webView.setWebViewClient(new CusWebViewClient());
        webView.setWebChromeClient(new CusWebChromeClient());
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setAllowFileAccess(true);
        String dir = getApplicationContext().getCacheDir().getAbsolutePath();

        setting.setDomStorageEnabled(true);// 开启 DOM storage API 功能
        setting.setGeolocationEnabled(true);
        setting.setUserAgentString("Mozilla/5.0 (Linux; Android 6.0.1; SM-G920V Build/MMB29K) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/52.0.2743.98 Mobile Safari/537.36");
        setting.setRenderPriority(RenderPriority.HIGH);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
        setting.setAppCachePath(dir);// 设置 Application Caches 缓存目录
        // 开启 Application Caches 功能
        setting.setAppCacheEnabled(true); // 开启 Application Caches 功能
        setting.setDefaultTextEncodingName("UTF-8");// 设置字符编码
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.v(TAG, "版本大于21，启动setMixedContentMode(0)");
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // 解决http及https混合情况下页面加载问题
        } else {
            Log.v(TAG, "版本小于21，无须启动setMixedContentMode(0)");
        }
        // 让网页适配手机
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
        webView.addJavascriptInterface(new Contact(), "contact");
        loadWeb(currentUrl);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        //使用系统的goback功能
        if (webView != null && webView.canGoBack()) {
            mLoadError = false;
            webView.goBack();
        } else {
            finish();
        }
    }


    /**
     * 刷新webview 自动注入参数
     *
     * @param url 是否强制刷新
     */
    public void loadWeb(String url) {
        if (TextUtils.isEmpty(url)) {
            Log.d(TAG, "无网页地址");
            return;
        }
        Log.d(TAG, "loadingUrl:" + url);
        webView.loadUrl(url);
        addUrlHistory(url);
    }

    private class CusWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {// 网页页面开始加载的时候
            if (WebActivity.this.isFinishing()) {
                return;
            }
            Log.e(TAG, "--onPageStarted()--, url : " + url);
            showProgress("加载中");
            webView.setEnabled(false);// 当加载网页的时候将网页进行隐藏
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {// 网页加载结束的时候
            Log.e(TAG, "onPageFinished , url : " + url);
            hidProgress();
            webView.setEnabled(true);
            if (!mLoadError) {
                webView.setVisibility(View.VISIBLE);
                errorWebView.setVisibility(View.GONE);
            }
//            testEvaluateJavascript();
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { // 网页加载时的连接的网址
            Log.d(TAG, "页面加载href:" + url);
            if (!url.contains("intent://"))
            {
                loadWeb(url);
            }
            return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.e(TAG, "onReceivedError, errorCode : " + errorCode + ", failingUrl : " + failingUrl + ", description : " + description);
            if (failingUrl.equals(currentUrl)) {
                loadFailtUrl();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            String reqUrl = request.getUrl().toString();
            Log.e(TAG, "onReceivedHttpError, reqUrl : " + reqUrl + ", currentUrl : " + currentUrl);
            if (reqUrl.equals(currentUrl)) {
                loadFailtUrl();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.e(TAG, "error : " + error);
            // 接受所有网站的证书
            handler.proceed();
            // 默认是handler.cancel();
//            super.onReceivedSslError(view, handler, error);
        }
    }

    static class CusWebChromeClient extends WebChromeClient
    {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            // 404 , 502 Bad Gateway...
            Log.e(TAG, "onReceivedTitle : " + title);
            super.onReceivedTitle(view, title);
        }
    }

    void loadFailtUrl() {
        mLoadError = true;
        errorWebView.loadUrl(ERROR_URL);

        errorWebView.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    /**
     * 打开一个新activity显示URL
     *
     * @param url
     */
    @UiThread
    protected void showUrl(String url, String title) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        startActivity(intent);
    }

    /**
     * 如果不是刷新，则添加到访问记录 记录的是原始地址
     *
     * @param newUrl 原始未添加软件参数的地址，防止记录了旧的token
     */
    private void addUrlHistory(String newUrl) {
        if (historyStack.size() == 0 || !WebActivity.this.currentUrl.equals(newUrl)) {
            Log.d(TAG, "添加到访问记录:" + newUrl);
            historyStack.push(newUrl);
        }
        // 使用新的url来刷新
        this.currentUrl = newUrl;
    }

    /*****************
     * JS部分
     **************************/

    private final class Contact {
        // JavaScript调用此方法

        /**
         * 关闭当前的原生界面
         */
        @JavascriptInterface
        public void goBack() {
            // 调用JS中的方法
            finish();
        }

        /**
         * 以原生界面activity的形式打开一个url
         *
         * @param url   url地址
         * @param title 界面显示的标题
         */
        @JavascriptInterface
        public void showActivity(String url, String title) {
            Log.d(TAG, "showActivity:" + url);
            showUrl(url, title);
        }

        /**
         * 跳转到支付宝支付界面(旧版的web方式支付)
         *
         * @param url
         */
        @JavascriptInterface
        public void showAliPayActivity(String url) {
            Log.d(TAG, "showAliPayActivity:" + url);
        }

        /**
         * 弹出一个原生的提示框
         *
         * @param title   标题
         * @param message 信息内容
         */
        @JavascriptInterface
        public void alert(String title, String message) {
            Log.d(TAG, "alert:" + title + message);
            // 要用uithread来更新，不然会卡住
            WebActivity.this.alert(title, message);
        }

        /**
         * 弹出登录框 判断当前的token是否是需要登录
         *
         * @param token
         * @return true 需要登录；false 不需要登录
         */
//        @JavascriptInterface
//        public boolean needLogin(String token) {
//            Log.d(TAG, "needLogin:" + token);
////            if (TextUtils.isEmpty(token) || Constants.TOKEN_GUEST.equals(token)) {
//            if (TextUtils.isEmpty(token) || DeviceUtil.generateUniqueCode(mContext).equals(token)) {
//                // 需要登录
//                Intent intent = new Intent(mContext, LoginActivity_.class);
//                intent.putExtra("mustAuth", true);
//                startActivity(intent);
//                return true;
//            } else {
//                // 不需要登录
//                return false;
//            }
//        }

        /**
         * 清除历史缓存记录 缓存清除后，后退会直接关闭当前原生界面
         */
        @JavascriptInterface
        public void clearHistory() {
            Log.d(TAG, "clearHistory:");
            historyStack.removeAllElements();
        }

        /**
         * 设置中央标题
         *
         * @param title
         */
        @JavascriptInterface
        public void setTitle(final String title) {
            Log.d(TAG, "setTitle:" + title);
            // 要用uithread来更新，不然会卡住
            changeTitle(title);
        }

        /**
         * 调用支付接口进行支持 支付成功回调javasript:onPayResult(int type,int errorCode,String
         * errorString)
         *
         * @param type               1，微信支付2支付宝
         * @param data               支付参数组成的JSON，具体看接口
         *                           微信：https://pay.weixin.qq.com/wiki/doc/api
         *                           /app/app.php?chapter=9_12&index=2
         * @param queryOrderUrl      订单支付结果查询url地址
         * @param successCallbackUrl 成功回调url地址
         * @param failureCallBackUrl 失败回调url地址
         * @param cancelCallBackUrl  取消支付回调url地址
         * @return 结果返回，0表示正常 -1表示未安装
         */
        @JavascriptInterface
        public int pay(int type, String data, String queryOrderUrl, String successCallbackUrl, String failureCallBackUrl, String cancelCallBackUrl) {
            Log.e(TAG, "data = " + data);
            Log.e(TAG, "queryOrderUrl = " + queryOrderUrl);
            Log.e(TAG, "successCallbackUrl = " + successCallbackUrl);
            Log.e(TAG, "failureCallBackUrl = " + failureCallBackUrl);
            Log.e(TAG, "cancelCallBackUrl = " + cancelCallBackUrl);
//            PayConstants.queryOrderUrl = queryOrderUrl;
//            PayConstants.successCallbackUrl = successCallbackUrl;
//            PayConstants.failureCallBackUrl = failureCallBackUrl;
//            PayConstants.cancelCallBackUrl = cancelCallBackUrl;
////            PayConstants.cancelCallBackUrl = ( ( TextUtils.isEmpty(cancelCallBackUrl) || !cancelCallBackUrl.startsWith("http")) ? failureCallBackUrl : cancelCallBackUrl);
//            PayConstants.isPropertyPay = false;
//            if (type == 1) {
//                boolean wxInstalledAndSupport = PayController.getInstance(WebActivity.this).isWxInstallAndSupport(WebActivity.this);
//                Log.d(TAG, "微信是否安装并且支持支付 ：" + wxInstalledAndSupport);
//                if (!wxInstalledAndSupport) {
//                    return -1;
//                } else {
//                    showMsg("微信启动中，如无反应，请手动打开微信！");
//                    wxPay(data);
//                }
//            } else if (type == 2) {
//                showMsg("支付宝启动中，如无反应，请手动打开支付宝！");
//                aliPay(data, WebActivity.this);
//            }
            return 0;
        }

        /**
         * 调用原生客户端从图库或者摄像头选择图片并上传
         * 可选择单张或者多张图片
         * 图片上传之后调用js返回结果给网页 onPictureSelected(String json)
         * json的结果为图片上传结果返回的消息bean ImagesUploadResult
         *
         * @param maxPicture 最大上传数量
         */
        @JavascriptInterface
        public void selectPicture(int maxPicture) {
            Log.v(TAG, "--selectPicture()-- maxPicture:" + maxPicture);
            //Toast.makeText(mContext, "能输入" + maxPicture, Toast.LENGTH_SHORT).show();
            showSelectPicture(maxPicture);

        }

        /**
         * 从历史stack一个一个的pop出来，知道页面与page相同
         * 即 http://1.1.1.1/XXXX.html?a=1
         * pop2Page(XXXX.html)跳到上一个XXXX.html
         * 如果是stack里面最后一个，则直接显示该地址
         *
         * @param webPage
         */
        @JavascriptInterface
        public void pop2Page(String webPage) {
            Log.v(TAG, "pop2Page:" + webPage);
            String popUrl = "";
            while (!historyStack.isEmpty()) {
                popUrl = historyStack.pop();
                if (popUrl.indexOf(webPage) > -1) {
                    break;
                }
            }
            if (!TextUtils.isEmpty(popUrl)) {
                loadWeb(popUrl);
            }
        }

        /**
         * 显示上一个页面
         */
        @JavascriptInterface
        public void popUrl() {
            Log.v(TAG, "popUrl()");
            popUrlAndShow();
        }

        /**
         * 切换全屏/普通
         *
         * @param isFulllScreen 是否全屏
         */
        @JavascriptInterface
        public void switchFullScreen(boolean isFulllScreen) {
            Log.d(TAG, "switchFullScreen " + isFulllScreen);
            handleSwitchFullScreen(isFulllScreen);
        }

        /**
         * 显示加载中的弹出框
         */
        @JavascriptInterface
        public void showProgressDialog() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgress("加载中...", true);
                }
            });
        }

        @JavascriptInterface
        public void closeProgressDialog() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hidProgress();
                }
            });
        }
    }

    /**
     * 调用js的方法
     *
     * @param id
     */
    private void onMoreMenu(int id) {
        Log.e(TAG, "--- onMoreMenu ---");
        webView.loadUrl("javascript:actionSheetDidSelected(" + id + ")");
    }

    @UiThread
    public void popUrlAndShow() {
        if (historyStack.size() > 1) {
            historyStack.pop();// 当前的页面
            String lastUrl = historyStack.pop();// 上一个页面
            loadWeb(lastUrl);// 打开上一个页面
        }
    }

    @UiThread
    public void showSelectPicture(int maxPicture) {
    }


    /***
     * 图片上传完成回调服务器接口
     *
     * @param json
     */
    public void onPictureSelected(String json) {
        webView.loadUrl("javascript:onPictureSelected('" + json + "')");
    }

    /**
     * 支付结果回调
     *
     * @param type        1，微信支付2支付宝
     * @param errorCode
     * @param errorString
     */
    public void onPayResult(int type, int errorCode, String errorString) {
        webView.loadUrl("javascript:onPayResult('" + type + "','" + errorCode + "','" + errorString + "')");
    }


    @UiThread
    public void changeTitle(String title) {
//        setCustomTitle(title);
    }

    @UiThread
    public void alert(String title, String message) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 切换全屏/普通
     *
     * @param isFulllScreen 是否全屏
     */
    @UiThread
    public void handleSwitchFullScreen(boolean isFulllScreen) {
        if (isFulllScreen) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(params);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            hideTitle();
        } else {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(params);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            unhideTitle();
//            hideTitleLine();
        }
    }

    // 权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
