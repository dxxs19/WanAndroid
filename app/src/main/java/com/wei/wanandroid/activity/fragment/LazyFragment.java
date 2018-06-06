package com.wei.wanandroid.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wei.wanandroid.activity.BaseFragment;

/**
 * 懒加载（延迟加载）
 *
 * @author: WEI
 * @date: 2018/6/5
 */
public abstract class LazyFragment extends BaseFragment {
    private boolean hasCreated = false, isFirstVisible = true, isFirstResume = true;

    /**
     * setUserVisibleHint()在Fragment实例化时会先调用一次，并且默认值是false，当选中当前显示的Fragment时还会再调用一次。
     * 此方法在fragment生命周期开始之前便被调用，运行在onAttach()之前。所以，它运行的时候，fragment都还没创建。
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e(TAG, "--- setUserVisibleHint ---, isVisibleToUser = " + isVisibleToUser + ", hasCreated = " + hasCreated);
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (isFirstVisible) {
                isFirstVisible = false;
                // 之前还没初始化
                initPrepare();
            } else {
                // 已经初始化
                onLazyResume();
            }
        } else {
            if (hasCreated)
            {
                onLazyPause();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "--- onActivityCreated ---");
        super.onActivityCreated(savedInstanceState);
        hasCreated = true;
        initPrepare();
    }

    @Override
    public void onResume()
    {
        Log.e(TAG, "--- onResume ---");
        super.onResume();
        if (isFirstResume)
        {  // 页面一加载的时候，所有fragment的生命周期都会执行到onResume。所以，第一次到这里的都停止，不再往后操作。
            isFirstResume = false;
            return;
        }
        Log.e(TAG, "onResume(), getUserVisibleHint() = " + getUserVisibleHint());
        if (getUserVisibleHint()) {
            // 用户可见状态
            onLazyResume();
        }
    }

    @Override
    public void onPause() {
        Log.e(TAG, "--- onPause ---");
        super.onPause();
        Log.e(TAG, "onPause(), getUserVisibleHint() = " + getUserVisibleHint());
        if (getUserVisibleHint()) {
            onLazyPause();
        }
    }

    private void initPrepare()
    {
        // 必须是用户可见状态并且fragment已经创建
        if (getUserVisibleHint() && hasCreated)
        {
            Log.e(TAG, "--- initPrepare ---");
            onFirstInit();
            onLazyResume();
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
     * fragment不可见（onPause 回调）
     */
    public abstract void onLazyPause();
}
