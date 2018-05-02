package com.wei.wanandroid.activity.rx;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: WEI
 * @date: 2018/5/2
 */
public abstract class HttpResultSubscriber<T> implements Observer<T>
{
    private Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        onFinal();
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    private void onFinal() {
    }

    public void cancel()
    {
        mDisposable.dispose();
    }

    public boolean isRunning()
    {
        return mDisposable.isDisposed();
    }
}
