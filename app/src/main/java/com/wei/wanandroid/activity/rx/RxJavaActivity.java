package com.wei.wanandroid.activity.rx;

import android.os.Bundle;
import android.util.Log;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author WEI
 */
public class RxJavaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
    }

    @Override
    public void initView() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //执行一些其他操作
                //.............
                //执行完毕，触发回调，通知观察者
                e.onNext("我来发射数据");
                e.onNext("I love beauty!");
                e.onNext("I love you!");
                e.onComplete();
            }
        })
                .map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                Log.e(TAG, " map -> apply() " + s);
                return s.length();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List list = new ArrayList();
                for (int i = 0; i < 3; i ++)
                {
                    list.add("I'm value " + integer);
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "subscribe -> accept() " + s);
            }
        });

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

}
