package com.wei.wanandroid.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author: WEI
 * @date: 2018/7/25
 */
public class CusButton extends android.support.v7.widget.AppCompatButton
{
    private final String TAG = getClass().getSimpleName();

    public CusButton(Context context) {
        super(context);
    }

    public CusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "--- dispatchTouchEvent ---");
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
        }
        return super.dispatchTouchEvent(ev);
//        return false;
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "--- onTouchEvent ---");
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
        }
//        return super.onTouchEvent(event);
        return false;
//        return true;
    }
}
