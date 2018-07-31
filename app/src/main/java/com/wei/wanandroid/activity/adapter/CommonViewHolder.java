package com.wei.wanandroid.activity.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.wanandroid.activity.image.CusAppGlideModule;

public class CommonViewHolder
{
    public View mConvertView;

    public CommonViewHolder(View convertView) {
        mConvertView = convertView;
    }

    public void setText(@IdRes int resId, String content)
    {
        View view = mConvertView.findViewById(resId);
        if (view instanceof TextView)
        {
            ((TextView)view).setText(content);
        }
    }

    public void setImageByPath(@IdRes int resId, String pathName)
    {
//        Bitmap bitmap = BitmapFactory.decodeFile(pathName);
//        setBitmapToView(bitmap, mConvertView.findViewById(resId));
        ImageView imageView = mConvertView.findViewById(resId);
//        CusAppGlideModule.with(mConvertView.getContext())
//                .load(pathName)
//                .into(imageView);
    }

    public void setImage(@IdRes int resId, Bitmap bitmap)
    {
        setBitmapToView(bitmap, mConvertView.findViewById(resId));
    }

    private void setBitmapToView(Bitmap bitmap, View view)
    {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageBitmap(bitmap);
        }
    }

}
