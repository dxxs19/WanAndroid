package com.wei.wanandroid.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * 懒加载（延迟加载）
 * @author: WEI
 * @date: 2018/6/5
 */
public abstract class LazyFragment extends Fragment
{
    protected final String TAG = getClass().getSimpleName();
    private boolean isFirstResume = true;
    private boolean isPrepared=false;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "--- onViewCreated ---");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Log.e(TAG, "--- onActivityCreated ---");
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        Log.e(TAG, "--- setUserVisibleHint ---" + isVisibleToUser + ", isFirstVisible = " + isFirstVisible
               + ", isFirstInvisible = " + isFirstInvisible);
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (isFirstVisible)
            {
                isFirstVisible = false;
                initPrepare();
            }
            else
            {
                onLazyResume();
            }
        }
        else
        {
            if (isFirstInvisible)
            {
                isFirstInvisible = false;
                onFirstUserInvisible();
            }
            else
            {
                onLazyPause();
            }
        }
    }

    @Override
    public void onResume()
    {
        Log.e(TAG, "--- onResume ---" + ", isFirstResume = " + isFirstResume);
        super.onResume();
        if (isFirstResume)
        {
            isFirstResume = false;
            return;
        }
        Log.e(TAG, "onResume(), getUserVisibleHint() = " + getUserVisibleHint());
        if (getUserVisibleHint())
        {
            onLazyResume();
        }
    }

    @Override
    public void onPause()
    {
        Log.e(TAG, "--- onPause ---");
        super.onPause();
        Log.e(TAG, "onPause(), getUserVisibleHint() = " + getUserVisibleHint());
        if (getUserVisibleHint())
        {
            onLazyPause();
        }
    }

    private synchronized void initPrepare()
    {
        Log.e(TAG, "--- initPrepare ---" + ", isPrepared = " + isPrepared);
        if (isPrepared) {
            onFirstInit();
            onLazyResume();
        }else{
            isPrepared = true;
        }
    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public abstract void onFirstInit();

    /**
     * fragment可见（onResume 回调）
     */
    public abstract void onLazyResume();

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public abstract void onFirstUserInvisible();

    /**
     * fragment不可见（onPause 回调）
     */
    public abstract void onLazyPause();
}
