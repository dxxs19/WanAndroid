package com.wei.wanandroid.activity.http;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.R;
import com.wei.wanandroid.service.MyService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * 服务器响应头
 * response.headers():
 *
 * Server: nginx/1.10.0 (Ubuntu)
 * Date: Mon, 14 May 2018 06:09:27 GMT
 * Content-Type: text/plain
 * Content-Length: 1759
 * Last-Modified: Tue, 27 May 2014 02:35:47 GMT
 * Connection: keep-alive
 * ETag: "5383fa03-6df"
 * Accept-Ranges: bytes
 * Cache-Control:
 * @author Administrator
 */
public class OkHttp3Activity extends BaseActivity
{
    public static final String TAG = "OkHttp3Activity";
    private final static String IMG_URL = "http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg";
    ImageView mImageView ;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http3);
        initOkHttpClient();
//        startService(new Intent(this, MyService.class));
    }

    @Override
    public void initView()
    {
        //        getAsynHttp();
//        postAsynHttp();
//        downAsynFile();
//        uploadMultipart();
        mImageView = findViewById(R.id.imgView);

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(OkHttp3Activity.this, OkHttp3Activity.class));
//            }
//        }, 5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPic(IMG_URL);
        showHelloWorld("http://www.publicobject.com/helloworld.txt");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "--- onNewIntent ---");
        String url = "http://g.hiphotos.baidu.com/zhidao/pic/item/1e30e924b899a901da2aece318950a7b0308f5cc.jpg";
        showPic(url);
    }

    private void showHelloWorld(String url)
    {
        Request request = new Request.Builder()
                .header("User-Agent", "OkHttp Demo!")
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "response : " + response + "; response content : " + response.body().toString());
            }
        });
    }

    private void initOkHttpClient()
    {
        File cacheFile = new File( getExternalCacheDir(), "okhttp-cache" );
        Cache cache = new Cache(cacheFile, 10 * 1024 * 1024);
        mOkHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
//                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        String cacheControl = request.cacheControl().toString();
        Log.e(TAG, "cacheControl : " + cacheControl);
        return originalResponse.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=2419200" )
                .removeHeader("Pragma")
                .build();
    };

    // 日志打印拦截器
    static class LoggingInterceptor  implements Interceptor
    {
        @Override
        public Response intercept(Chain chain) throws IOException
        {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.e(TAG, String.format("Sending request %s on %s%n%s", request.url(),  chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.e(TAG, String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            // LoggingInterceptor : Response{protocol=http/1.1, code=200, message=OK, url=http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg}
            Log.e(TAG, "LoggingInterceptor : " + response);
            return response;
        }
    }

    final class GzipRequestInterceptor implements Interceptor {
        @Override public Response intercept(Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
                return chain.proceed(originalRequest);
            }

            Request compressedRequest = originalRequest.newBuilder()
                    .header("Content-Encoding", "gzip")
                    .method(originalRequest.method(), gzip(originalRequest.body()))
                    .build();
            return chain.proceed(compressedRequest);
        }

        private RequestBody gzip(final RequestBody body) {
            return new RequestBody() {
                @Override public MediaType contentType() {
                    return body.contentType();
                }

                @Override public long contentLength() {
                    return -1; // We don't know the compressed length in advance!
                }

                @Override public void writeTo(BufferedSink sink) throws IOException {
                    BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                    body.writeTo(gzipSink);
                    gzipSink.close();
                }
            };
        }
    }

    /**
     * 异步GET请求
     */
    private void getAsynHttp()
    {
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, request.body().toString());
            }
        });
    }


    /**
     * 异步POST请求
     */
    private void postAsynHttp()
    {
        RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .build();
        Request request = new Request.Builder()
                .url("http://api.1-blog.com/biz/bizserver/article/list.do")
                .post(formBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, response.body().string());
            }
        });
    }

    /**
     * 异步下载文件
     */
    private void downAsynFile()
    {
        Request request = new Request.Builder()
                .url("http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg")
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream;
                fileOutputStream = new FileOutputStream(new File("/sdcard/xx.jpg"));
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1)
                {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
                showMsg("文件下载成功！", 500);
            }
        });
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private void uploadMultipart()
    {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "xxx")
                .addFormDataPart("image", "xxx.jpg", RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/xxx.jpg")))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure : " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse : " + response.body().string());
            }
        });
    }

    private void showPic(String url)
    {
        Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "请求失败：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final byte[] bytes = response.body().bytes();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapFactory.decodeByteArray( bytes, 0, bytes.length);
                        if (mImageView != null)
                        {
                            mImageView.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        });
    }

}
