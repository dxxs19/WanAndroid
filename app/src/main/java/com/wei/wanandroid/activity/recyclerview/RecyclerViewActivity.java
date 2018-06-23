package com.wei.wanandroid.activity.recyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wei.utillibrary.ImageUtil;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.activity.adapter.RecyclerAdapter;
import com.wei.wanandroid.bean.BeautyBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class RecyclerViewActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    private List<BeautyBean> mDatas;
    private RecyclerAdapter<BeautyBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
    }

    @Override
    public void initView() {
        initData();
        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new RecyclerAdapter<>(this, mDatas, R.layout.item_recycler_beauty, (data, recyclerViewHolder) -> {
            recyclerViewHolder.setImage(R.id.imgView_beauty, ImageUtil.getOptsBitmap(data.getFilePath(), 100, 200));
            recyclerViewHolder.setText(R.id.tv_des, data.getFileName());
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData()
    {
        mDatas = new ArrayList<>();
        File picFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File targetFile = new File(picFile.getAbsolutePath(), "锁屏壁纸");
        if (targetFile.exists())
        {
            String[] strArr = targetFile.list();
            if (strArr != null && strArr.length > 0) {
                for (String name : strArr) {
                    mDatas.add( new BeautyBean(targetFile.getAbsolutePath() + File.separator + name, name) );
                }
            }
        }
    }
}
