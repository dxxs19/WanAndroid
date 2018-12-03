package com.wei.wanandroid.activity.image;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
//                .apply(getCircleCropTransformOptions())
                .apply(getRoundedCornersOptions())
                .into(imageView);
    }

    // 加载圆形图片
    private RequestOptions getCircleCropTransformOptions() {
        RequestOptions requestOptions = RequestOptions.circleCropTransform()
                .placeholder(R.drawable.logo_yuanjiao_120)
                .override(300, 250)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不做磁盘缓存
                .skipMemoryCache(true);  // 不做内存缓存
        return requestOptions;
    }

    // 加载圆角图片
    private RequestOptions getRoundedCornersOptions() {
        RoundedCorners roundedCorners = new RoundedCorners(30);
        RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners)
                .placeholder(R.drawable.logo_yuanjiao_120)
                .override(300, 250)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        return requestOptions;
    }
}
