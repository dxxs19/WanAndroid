package com.wei.wanandroid.activity.rx;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author WEI
 */
public class RxJavaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        // 测试内存泄露
        MyHandler myHandler = new MyHandler(this);
        Message message = myHandler.obtainMessage();
        message.obj = "I love beauty!";
        myHandler.sendMessage(message);
    }

    static class MyHandler extends Handler
    {
        SoftReference<RxJavaActivity> softReference;

        public MyHandler(RxJavaActivity context) {
            softReference = new SoftReference<>(context);
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
            RxJavaActivity context = softReference.get();
            Log.e(RxJavaActivity.class.getSimpleName(), "context = " + context);
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
        Observable observable = Observable.just("I love beautyleg!");
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

}
