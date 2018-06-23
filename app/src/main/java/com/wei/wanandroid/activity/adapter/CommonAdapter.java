package com.wei.wanandroid.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class CommonAdapter<T> extends BaseAdapter
{
    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    private IDealWithData<T> mDealWithData;

    public CommonAdapter(Context context, List<T> datas, int layoutId, IDealWithData<T> dealWithData) {
        mContext = context;
        mDatas = datas;
        mLayoutId = layoutId;
        mDealWithData = dealWithData;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.e(getClass().getSimpleName(), "convertView = " + convertView + ", postition = " + position);
        CommonViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
            holder = new CommonViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (CommonViewHolder) convertView.getTag();
        }
        Log.e(getClass().getSimpleName(), "convertView = " + convertView);
        T bean = (T) getItem(position);
        if (null != bean) {
            mDealWithData.bindDataToViewHolder(bean, holder);
        }
        return convertView;
    }

    public interface IDealWithData<T>
    {
        void bindDataToViewHolder(T bean, CommonViewHolder viewHolder);
    }

}
