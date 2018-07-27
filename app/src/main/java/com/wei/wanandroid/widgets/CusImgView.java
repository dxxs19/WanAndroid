package com.wei.wanandroid.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

import com.wei.wanandroid.R;

/**
 * @author: WEI
 * @date: 2017/11/15
 */

public class CusImgView extends android.support.v7.widget.AppCompatImageView
{
    private final String TAG = getClass().getSimpleName();
    int mScreenHeight, mScreenWidth;
    float lastX, lastY;
    int mLeft, mTop, mRigh, mBottom;
    private Scroller mScroller;

    public CusImgView(Context context) {
        this(context, null);
    }

    public CusImgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context)
    {
        DisplayMetrics displayMetrics  = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        // 1080 x 1920
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        Log.e( TAG, mScreenWidth + ", " + mScreenHeight );
        setImageResource(R.mipmap.ic_wave);
        mLeft = getLeft();
        mTop = getTop();
        mRigh = getRight();
        mBottom = getBottom();
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset())
        {
            ((View)getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    // 5.
    public void smoothScrollTo(int destX, int destY)
    {
        int scrollx = getScrollX();
        int delta = destX - scrollx;
        mScroller.startScroll(scrollx, 0, delta, 0, 2000);
        // invalidate只回调onDraw，一般用于刷新当前view，使当前view进行重绘，不会进行测量及布局;
        // requestLayout则顺序回调onMeasure->onLayout->onDraw。需要父布局对其进行重新测量、布局、绘制时使用;
        // requestLayout可以替代invalidate，反过来则不行。
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "--- dispatchTouchEvent ---");
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
        return super.dispatchTouchEvent(event);
//        return false;
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) (x - lastX);
                int offsetY = (int) (y - lastY);
                int leftPos = getLeft() + offsetX;
                int topPos = getTop() + offsetY;
                int rightPos = getRight() + offsetX;
                int bottomPos = getBottom() + offsetY;
                if ( leftPos < 0 )
                {
                    leftPos = 0;
                    rightPos = getWidth();
                }
                else if (leftPos >= ( mScreenWidth-getWidth()) )
                {
                    leftPos = mScreenWidth - getWidth();
                    rightPos = mScreenWidth;
                }
                if ( topPos < 0 )
                {
                    topPos = 0;
                    bottomPos = getBottom();
                }
                else if (topPos >= (mScreenHeight-getHeight()))
                {
                    topPos = mScreenHeight - getHeight();
                    bottomPos = mScreenHeight;
                }

                // 1
//                layout(leftPos, topPos, rightPos, bottomPos);

                // 2
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);

                // 3
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
                params.leftMargin = leftPos;
                params.topMargin = topPos;
                // 布局有改变，此处只能用requestLayout来要求父布局对其进行重新测量、布局、绘制。
                // 不能调用invalidate，因为invalidate没有重新测量、布局回调。
                // setLayoutParams源码也是调用了requestLayout来更新布局
                requestLayout();
//                setLayoutParams(params);

                // 4.父容器里面的子控件都一起动
//                ((View)getParent()).scrollBy(-offsetX, - offsetY);
                break;

            case MotionEvent.ACTION_UP:
//                ((View)getParent()).scrollTo( mLeft , mTop);
                break;

            default:
                break;
        }
        Log.e(TAG, "--- onTouchEvent ---");
        return super.onTouchEvent(event);
//        return false;
//        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "--- onMeasure ---" + ", " + widthMeasureSpec + ", " + heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "--- onLayout ---" + ", " + changed + ", " + left + ", " + top + ", " + right + ", " + bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.e(TAG, "--- onDraw ---" );
    }
}
