package com.wei.wanandroid.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.List;

public class CommonAdapter<T> extends BaseAdapter implements AbsListView.OnScrollListener
{
    private static final String TAG = "CommonAdapter";
    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    private IDealWithData<T> mDealWithData;
    private int mFirstPosition, mLastPosition;

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
        T bean = getItem(position);
        mPosition = position;
        if (null != bean) {
            mDealWithData.bindDataToViewHolder(bean, holder);
        }
        return convertView;
    }

    public int mPosition;
    public int getPosition()
    {
        return mPosition;
    }

    private boolean isLoadAccess;
    public boolean isLoadAccess()
    {
        return isLoadAccess;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        isLoadAccess = (scrollState == SCROLL_STATE_IDLE);
        if (isLoadAccess)
        {
            Log.e(TAG, "滑动结束");
            notifyDataSetChanged();
        }else{
            Log.e(TAG, "正在滑动");
        }
        mFirstPosition = view.getFirstVisiblePosition();
        mLastPosition = view.getLastVisiblePosition();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public boolean verifyPositionNeedRequestGetView(int position)
    {
        return position < mFirstPosition || position > mLastPosition;
    }

    public interface IDealWithData<T>
    {
        void bindDataToViewHolder(T bean, CommonViewHolder viewHolder);
    }

}
