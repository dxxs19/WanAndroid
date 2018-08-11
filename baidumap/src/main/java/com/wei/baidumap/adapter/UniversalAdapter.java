package com.wei.baidumap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

/**
 * 通用的adapter类
 * author: WEI
 * date: 2017/6/14
 */

public abstract class UniversalAdapter<T> extends BaseAdapter
{
    protected Context mContext;
    protected List<T> mDatas;
    protected int mItemLayoutId;

    public UniversalAdapter(Context context, List<T> datas, int itemLayoutId)
    {
        mContext = context;
        mDatas = datas;
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final UniversalViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getContentView();
    }

    /**
     * 把item里面的内容相应的填充到viewHolder里面的控件上
     * @param viewHolder
     * @param item
     */
    protected abstract void convert(UniversalViewHolder viewHolder, T item);

    private UniversalViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return UniversalViewHolder.getViewHolder(mContext, convertView, parent, mItemLayoutId, position);
    }
}
