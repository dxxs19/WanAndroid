package com.wei.wanandroid.activity.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>
{
    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    private IRecyclerCallback<T> mRecyclerCallback;

    public RecyclerAdapter(Context context, List<T> datas, @LayoutRes int layoutId, IRecyclerCallback<T> recyclerCallback)
    {
        mContext = context;
        mDatas = datas;
        mLayoutId = layoutId;
        mRecyclerCallback = recyclerCallback;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("RecyclerAdapter", "--- onCreateViewHolder ---, parent = " + parent + ", viewType = " + viewType);
        return new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        mRecyclerCallback.bindDataToViewHolder(getItem(position), holder);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    public interface IRecyclerCallback<T>
    {
        void bindDataToViewHolder(T data, RecyclerViewHolder recyclerViewHolder);
    }
}
