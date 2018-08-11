package com.wei.baidumap.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 通用的ViewHolder类
 * author: WEI
 * date: 2017/6/14
 */

public class UniversalViewHolder
{
    private SparseArray<View> mViewSparseArray;
    private View mContentView;
    private int mPosition;

    private UniversalViewHolder(Context context, ViewGroup parent, int layoutId, int position)
    {
        mPosition = position;
        mViewSparseArray = new SparseArray<>();
        mContentView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mContentView.setTag(this);
    }

    /**
     * 获取ViewHolder对象
     * @param context
     * @param contentView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static UniversalViewHolder getViewHolder(Context context, View contentView, ViewGroup parent, int layoutId, int position)
    {
        if (contentView == null)
        {
            return new UniversalViewHolder(context, parent, layoutId, position);
        }
        return (UniversalViewHolder) contentView.getTag();
    }

    /**
     * 通过viewId获取相应的view，如果没有则加入sparse数组
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViewSparseArray.get(viewId);
        if (null == view)
        {
            view = mContentView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public View getContentView()
    {
        return mContentView;
    }

    /**
     * 为TextView设置字符串
     * @param viewId
     * @param text
     * @return
     */
    public UniversalViewHolder setText(int viewId, String text)
    {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public UniversalViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public UniversalViewHolder setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public UniversalViewHolder setImageByUrl(Context context, int viewId, String url)
    {
//        setImageByUrl(context, viewId, url, R.drawable.ic_adv_default, R.drawable.ic_adv_default);
        return this;
    }

    public UniversalViewHolder setImageByUrl(Context context, int viewId, String url, int defaultResId, int failtResId)
    {
//        ImageView imgView = getView(viewId);
//        SyncImageLoader.getInstance(context).displayImage(imgView, url, defaultResId, failtResId);
        return this;
    }

    public int getPosition()
    {
        return mPosition;
    }
}
