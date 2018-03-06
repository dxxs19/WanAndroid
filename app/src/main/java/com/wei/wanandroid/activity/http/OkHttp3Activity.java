package com.wei.wanandroid.activity.http;

import android.os.Bundle;
import android.util.Log;

import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Administrator
 */
public class OkHttp3Activity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http3);
    }

    @Override
    public void initView() {
        //        getAsynHttp();
//        postAsynHttp();
//        downAsynFile();
        uploadMultipart();
    }

    /**
     * 异步GET请求
     */
    private void getAsynHttp()
    {
        Request.Builder builder = getBuilder("http://www.baidu.com");
        builder.method("GET", null);
        Request request = builder.build();
        setRequestCallBack(new RequestCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse())
                {
                    Log.e(TAG, "cache : " + response.cacheResponse().toString());
                }
                else
                {
                    response.body().string();
                    String str = response.networkResponse().toString();
                    Log.e(TAG, "network : " + str);
                }
                showMsg("请求成功！", 500);
            }
        });
        requestCall(request);
    }


    /**
     * 异步POST请求
     */
    private void postAsynHttp()
    {
        RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .build();
        Request request = getBuilder("http://api.1-blog.com/biz/bizserver/article/list.do")
                .post(formBody)
                .build();
        setRequestCallBack(new RequestCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "failure : " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e(TAG, str);
                showMsg("请求成功！", 500);
            }
        });
        requestCall(request);
    }

    /**
     * 异步下载文件
     */
    private void downAsynFile()
    {
        String url = "http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg";
        Request request = getBuilder(url).build();
        setRequestCallBack(new RequestCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

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
        requestCall(request);
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private void uploadMultipart()
    {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "xxx")
                .addFormDataPart("image", "xxx.jpg", RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/xxx.jpg")))
                .build();
        Request request = getBuilder("https://api.imgur.com/3/image")
                .header("Authorization", "Client-ID " + "...")
                .post(requestBody)
                .build();
        setRequestCallBack(new RequestCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure : " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse : " + response.body().string());
            }
        });
        requestCall(request);
    }

}
