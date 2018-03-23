package com.wei.wanandroid.activity.image;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

public class GlideActivity extends BaseActivity
{
    ImageView mImageView ;
    String url = "http://p1.pstatp.com/large/166200019850062839d3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
    }

    @Override
    public void initView() {
        mImageView = findViewById(R.id.imgView);
    }

    void showImage(View view)
    {
        Glide.with(this).load(url).into(mImageView);
    }
}
