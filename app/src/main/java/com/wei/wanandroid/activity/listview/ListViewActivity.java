package com.wei.wanandroid.activity.listview;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import com.wei.utillibrary.ImageUtil;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.activity.adapter.BeautyAdapter;
import com.wei.wanandroid.activity.adapter.CommonAdapter;
import com.wei.wanandroid.bean.BeautyBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends BaseActivity
{
    ListView mListView;
    private List<BeautyBean> mBeauties;
//    private BeautyAdapter mBeautyAdapter;
    private CommonAdapter<BeautyBean> mBeautyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
    }

    @Override
    public void initView()
    {
        mListView = findViewById(R.id.list_view);
        initData();
//        mBeautyAdapter = new BeautyAdapter(this, mBeauties);
        mBeautyAdapter = new CommonAdapter<>(this, mBeauties, R.layout.item_recycler_beauty,
                (bean, viewHolder) -> {
                    viewHolder.setText(R.id.tv_des, bean.getFileName());

                    if (mBeautyAdapter.isLoadAccess())
                    {
//                        viewHolder.setImage(R.id.imgView_beauty, ImageUtil.getOptsBitmap(bean.getFilePath(), 300, 600));
                        viewHolder.setImageByPath(R.id.imgView_beauty, bean.getFilePath());
                    }else
                    {
                        if (mBeautyAdapter.verifyPositionNeedRequestGetView(mBeautyAdapter.getPosition()))
                        {
                            viewHolder.setImage(R.id.imgView_beauty, null);
                        }
                    }

                });
        mListView.setOnScrollListener(mBeautyAdapter);
        mListView.setAdapter(mBeautyAdapter);
    }

    private void initData()
    {
        mBeauties = new ArrayList<>();
        File picFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File targetFile = new File(picFile.getAbsolutePath(), "锁屏壁纸");
        if (targetFile.exists())
        {
            String[] strArr = targetFile.list();
            if (strArr != null && strArr.length > 0) {
                for (String name : strArr)
                {
                    mBeauties.add( new BeautyBean(targetFile.getAbsolutePath() + File.separator + name, name) );
                }
            }
        }
    }
}
