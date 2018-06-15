package com.wei.wanandroid.activity.http.retrofit.apiservice;

import com.wei.wanandroid.activity.http.retrofit.model.FuliBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * @author: WEI
 * @date: 2018/6/14
 */
public interface ApiService
{
//    @Headers("Cache-Control: max-age=640000")
//    @GET("福利/{pageSize}/{pageIndex}")
//    Call<FuliBean> getFuli(@Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);

//    @HTTP(method = "GET", path = "福利/{pageSize}/{pageIndex}")
//    Call<FuliBean> getFuli(@Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);

    @HTTP(method = "GET", path = "福利/{pageSize}/{pageIndex}")
    Observable<FuliBean> getFuli(@Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);

    @GET("all/{pageSize}/{pageIndex}")
    Call<FuliBean> getAll(@Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);

}
