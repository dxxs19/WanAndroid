package com.wei.wanandroid.activity.rx;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.ref.SoftReference;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
        testFlowable();
    }

    private void testFlowable()
    {
        // 背压一般用于异步订阅（观察者与被观察者不在同一线程）：执行结果为先发送完所有事件，现在观察者那边回调onNext（）。
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e)
            {
                Log.e(TAG, "观察者可接收事件数：" + e.requested());
                // 一共发送4个事件
                Log.d(TAG, "发送事件 1");
                e.onNext(1);
                Log.d(TAG, "发送事件 2");
                e.onNext(2);
                Log.d(TAG, "发送事件 3");
                e.onNext(3);
                Log.d(TAG, "发送事件 4");
                e.onNext(4);
                Log.d(TAG, "发送完成");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 作用：决定观察者能够接收多少个事件
                        // 如设置了s.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
                        // 官方默认推荐使用Long.MAX_VALUE，即s.request(Long.MAX_VALUE);
                        s.request(90);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
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
            public void subscribe(ObservableEmitter e) throws Exception
            {
                // 同步订阅(观察者与被观察者在同一线程):发一个事件，就会回调一次观察者的onNext。必须等观察者的onNext执行完毕才能发送下一个事件
                Log.e(TAG, "发送1");
                e.onNext("I love rx!");
                Log.e(TAG, "发送2");
                e.onNext("I love bt!");
                Log.e(TAG, "发送3");
                e.onNext("good");
                Log.e(TAG, "发送完成");
                e.onComplete();
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
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
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
