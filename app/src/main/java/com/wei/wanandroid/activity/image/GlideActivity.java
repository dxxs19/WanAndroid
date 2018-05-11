package com.wei.wanandroid.activity.image;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;

public class GlideActivity extends BaseActivity
{
    ImageView mImageView, mOtherImgView ;
    String gifUrl = "http://p1.pstatp.com/large/166200019850062839d3";
    String beautyUrl = "https://gd4.alicdn.com/imgextra/i2/1066012351/TB2XW4DcMaTBuNjSszfXXXgfpXa_!!1066012351.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
    }

    @Override
    public void initView() {
        mImageView = findViewById(R.id.imgView);
        mOtherImgView = findViewById(R.id.imgView_other);
    }

    public void showImage(View view)
    {
        showInView(beautyUrl, mImageView);
        showInView("https://gd1.alicdn.com/imgextra/i2/0/TB1IQZwLVXXXXcgXXXXXXXXXXXX_!!0-item_pic.jpg", mOtherImgView);
    }

    private void showInView(String url, ImageView imageView) {
        GlideApp.with(this)
//                .load(gifUrl)
                .load(url)
//                .load(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.logo_yuanjiao_120)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(900, 750)
                // 跳过缓存，即不缓存。
//                .skipMemoryCache(true)
                .into(imageView);
    }
}
