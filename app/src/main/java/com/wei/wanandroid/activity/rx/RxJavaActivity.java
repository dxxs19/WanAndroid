package com.wei.wanandroid.activity.rx;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

import java.lang.ref.SoftReference;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author WEI
 */
public class RxJavaActivity extends BaseActivity
{
    public static final String BEAUTY_URL = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        // 测试内存泄露
//        MyHandler myHandler = new MyHandler(this);
//        Message message = myHandler.obtainMessage();
//        message.obj = "I love beauty!";
//        myHandler.sendMessageDelayed(message, 360*1000);
        request();
    }

    private void request()
    {
        Observable.create(new ObservableOnSubscribe<BeautyPicRespJson>() {
            @Override
            public void subscribe(final ObservableEmitter<BeautyPicRespJson> e) {
                getCall(getRetrofitRequest()).enqueue(new Callback<BeautyPicRespJson>() {
                    @Override
                    public void onResponse(Call<BeautyPicRespJson> call, Response<BeautyPicRespJson> response) {
                        e.onNext(response.body());
                        e.onComplete();
                    }

                    @Override
                    public void onFailure(Call<BeautyPicRespJson> call, Throwable t) {
                        e.onError(t);
                    }
                });
            }
        }).subscribe(mPicRespJsonObserver);
    }

    Observer<BeautyPicRespJson> mPicRespJsonObserver = new HttpResultSubscriber<BeautyPicRespJson>() {
        @Override
        public void onSuccess(BeautyPicRespJson beautyPicRespJson) {
            if (!beautyPicRespJson.isError()) {
                List<BeautyPicRespJson.BeautiesBean> results = beautyPicRespJson.getResults();
                for (BeautyPicRespJson.BeautiesBean bean : results) {
                    Log.e(TAG, bean.getUrl());
                }
            }
        }
    };

    static class MyHandler extends Handler
    {
//        SoftReference<RxJavaActivity> softReference;
        Context mContext;

        public MyHandler(RxJavaActivity context) {
            // 一定要是强引用才能导致内存泄露，软引用等不会引起内存泄露或者说不会被leakcanary监测到
//            softReference = new SoftReference<>(context);
            mContext = context;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(RxJavaActivity.class.getSimpleName(), "--- handleMessage ---" + msg.obj);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            RxJavaActivity context = softReference.get();
            Log.e(RxJavaActivity.class.getSimpleName(), "mContext = " + mContext);
        }
    }

    @Override
    public void initView() {

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                //执行一些其他操作
//                //.............
//                //执行完毕，触发回调，通知观察者
//                e.onNext("我来发射数据");
//                e.onNext("I love beauty!");
//                e.onNext("I love you!");
//                e.onComplete();
//            }
//        }).map(new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) throws Exception {
//                Log.e(TAG, " map -> apply() " + s);
//                return s.length();
//            }
//        }).flatMap(new Function<Integer, ObservableSource<String>>() {
//            @Override
//            public ObservableSource<String> apply(Integer integer) throws Exception {
//                List list = new ArrayList();
//                for (int i = 0; i < 3; i ++)
//                {
//                    list.add("I'm value " + integer);
//                }
//                return Observable.fromIterable(list);
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e(TAG, "subscribe -> accept() " + s);
//            }
//        });

        // 通过just创建的observable会自动调用onComplete；而通过create创建的observable不会自动调用onComplete。
        Observable observable = Observable.just("I love rxjava!");
        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext("I love rx!");
            }
        });
        observable1.subscribe(mObserver);
    }

    Observer<Object> mObserver = new Observer<Object>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.e(TAG, "-- onSubscribe --" + d.toString());

        }

        @Override
        public void onNext(Object s) {
            Log.e(TAG, "-- onNext --" + s);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "-- onError --" + e.getMessage());
        }

        @Override
        public void onComplete() {
            Log.e(TAG, "-- onComplete ---");
        }
    };

    private Call<BeautyPicRespJson> getCall(RetrofitRequest retrofitRequest) {
        return retrofitRequest.getPics(20, 1);
    }

    private RetrofitRequest getRetrofitRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BEAUTY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitRequest.class);
    }
}
