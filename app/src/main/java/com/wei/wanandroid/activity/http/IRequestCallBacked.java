package com.wei.wanandroid.activity.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author: WEI
 * @date: 2017/11/6
 */

public interface IRequestCallBacked
{
    void onFailure(Call call, IOException e);
    void onResponse(Call call, Response response) throws IOException;
}
