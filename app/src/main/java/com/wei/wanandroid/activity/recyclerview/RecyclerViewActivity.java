package com.wei.wanandroid.activity.recyclerview;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class RecyclerViewActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        initData();
        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData()
    {
        mDatas = new ArrayList<>();
//        for (int i = 'A'; i <= 'z'; i++) {
//            mDatas.add("" + (char) i);
//        }
        File picFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File targetFile = new File(picFile.getAbsolutePath(), "锁屏壁纸");
        if (targetFile.exists())
        {
            String[] strArr = targetFile.list();
            if (strArr != null && strArr.length > 0) {
                for (String name : strArr) {
                    mDatas.add(targetFile.getAbsolutePath() + File.separator + name);
                }
            }
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this).
                    inflate(R.layout.item_recycler_beauty, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
            holder.mTextView.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;
            ImageView mPicImgView;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.tv_des);
                mPicImgView = itemView.findViewById(R.id.imgView_beauty);
            }
        }
    }
}
