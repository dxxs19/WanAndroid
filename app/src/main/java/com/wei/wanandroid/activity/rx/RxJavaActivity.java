package com.wei.wanandroid.activity.rx;

import android.os.Bundle;
import android.util.Log;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

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
    }

    @Override
    public void initView() {
        mObservable.subscribe(mObserver);
    }

    Observable<String> mObservable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {
            //执行一些其他操作
            //.............
            //执行完毕，触发回调，通知观察者
            e.onNext("我来发射数据");
        }
    });

    Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.e(TAG, "-- onSubscribe --" + d.toString());
        }

        @Override
        public void onNext(String s) {
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
