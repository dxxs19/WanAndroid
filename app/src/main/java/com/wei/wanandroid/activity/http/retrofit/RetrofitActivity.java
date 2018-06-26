package com.wei.wanandroid.activity.http.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.activity.http.OkHttp3Activity;
import com.wei.wanandroid.activity.http.retrofit.apiservice.ApiService;
import com.wei.wanandroid.activity.http.retrofit.converter.StringGsonConverterFactory;
import com.wei.wanandroid.activity.http.retrofit.model.FuliBean;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends BaseActivity
{
    private String base_url = "http://gank.io/api/data/";
    Retrofit mRetrofit;

    public static void startRetrofitActivity(OkHttp3Activity context) {
        context.startActivity(new Intent(context, RetrofitActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
    }

    @Override
    public void initView() {
        initRetrofit();
    }

    private void initRetrofit()
    {
        // http://gank.io/api/data/福利/10/1
        mRetrofit = new Retrofit.Builder()
                // 数据格式转换，不能省略
                .addConverterFactory(StringGsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                // 这样，api请求接口可以返回Observable类型对象，用于与RxJava结合使用。
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(base_url)
                .build();
        // 动态代理
        ApiService service = mRetrofit.create(ApiService.class);
        // Observable
        service.getFuli(10, 2)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<FuliBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "isDisposed : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(FuliBean fuliBean) {
                        List<FuliBean.ResultsBean> results = fuliBean.getResults();
                        if (results != null && results.size() > 0)
                        {
                            for (FuliBean.ResultsBean bean:results) {
                                Log.e(TAG, bean.toString());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });

//        service.getFuli(10, 1).enqueue(new Callback<FuliBean>() {
//            @Override
//            public void onResponse(Call<FuliBean> call, Response<FuliBean> response) {
//                Log.e(TAG, "onResponse : " + call.toString());
        //  加入Gson数据转换器之后，回调中直接就是需要的对象类型
//                FuliBean fuliBean = response.body();
//                List<FuliBean.ResultsBean> results = fuliBean.getResults();
//                if (results != null && results.size() > 0)
//                {
//                    for (FuliBean.ResultsBean bean:results) {
//                        Log.e(TAG, bean.toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FuliBean> call, Throwable t) {
//                Log.e(TAG, "onFailure : " + t.getMessage());
//            }
//        });
    }

}
