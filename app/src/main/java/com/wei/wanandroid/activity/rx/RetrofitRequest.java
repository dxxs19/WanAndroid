package com.wei.wanandroid.activity.rx;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author: WEI
 * @date: 2018/5/2
 */
public interface RetrofitRequest {

    @GET("{pageSize}/{pageIndex}")
    Call<BeautyPicRespJson> getPics(@Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);
}
