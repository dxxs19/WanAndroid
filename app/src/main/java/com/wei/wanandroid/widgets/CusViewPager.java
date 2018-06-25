package com.wei.wanandroid.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * @author: WEI
 * @date: 2018/6/25
 */
public class CusViewPager extends ViewPager
{
    private final static String TAG = "CusViewPager";

    public CusViewPager(@NonNull Context context) {
        super(context);
    }

    public CusViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev)
//    {
//        boolean intercept = false;
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                // 不能拦截，否则无法传递事件给子元素
//                intercept = false;
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                // 针对不同的滑动冲突，只需要修改这个条件即可，其它均不需做修改并且也不能修改
//                if (isChildScrollToRight())
//                {
//                    // 拦截事件
//                    intercept = true;
//                }
//                else
//                {
//                    // 不拦截事件
//                    intercept = false;
//                }
//                break;
//
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                // 不拦截，否则子元素可能无法接收到这两个事件
//                intercept = false;
//                break;
//
//            default:
//        }
//        Log.e(TAG, "intercept : " + intercept);
//        return intercept;
//    }

    /**
     * 是否已经滑到了最右边
     *
     * @return
     */
    private boolean isChildScrollToRight()
    {
        View childView = getChildAt(0);
        Log.e(TAG, "childView : " + childView);
        if ( childView != null && childView instanceof CusHorizontalScrollView)
        {
            return ((CusHorizontalScrollView) childView).isScrollToRight();
        }
        return true;
    }

    /**
     * 是否已经滑到了最左边
     * @return
     */
    private boolean isChildScrollToLeft() {
        View childView = getChildAt(0);
        if ( childView != null && childView instanceof CusHorizontalScrollView)
        {
            return ((CusHorizontalScrollView) childView).isScrollToLeft();
        }
        return true;
    }

}
