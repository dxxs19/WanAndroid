package com.wei.wanandroid.activity.eventdispatch;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

public class EventDispatchActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);
    }

    @Override
    public void initView() {
        Button leftBtn = findViewById(R.id.btn_left);
        Button rightBtn = findViewById(R.id.btn_right);
        Log.e(TAG, "mScreenWidth : " + mScreenWidth + ", mDensity : " + mDensity);
        ViewGroup.LayoutParams leftLayoutParams = leftBtn.getLayoutParams();
        ViewGroup.LayoutParams rightLayoutParams = rightBtn.getLayoutParams();
        if (mScreenWidth < 380 * mDensity)
        {
            leftLayoutParams.width = mScreenWidth/2;
            rightLayoutParams.width = mScreenWidth/2;
        }
        leftBtn.setLayoutParams(leftLayoutParams);
        rightBtn.setLayoutParams(rightLayoutParams);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
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
    public boolean onTouchEvent(MotionEvent event)
    {
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
        return super.onTouchEvent(event);
//        return false;
    }

}
