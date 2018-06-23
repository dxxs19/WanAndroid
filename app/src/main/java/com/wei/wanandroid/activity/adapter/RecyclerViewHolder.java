package com.wei.wanandroid.activity.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder
{
    private View mConvertView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
    }

    public void setText(@IdRes int resId, String content)
    {
        View view = mConvertView.findViewById(resId);
        if (view instanceof TextView)
        {
            ((TextView)view).setText(content);
        }
    }

    public void setImage(@IdRes int resId, String pathName)
    {
        Bitmap bitmap = BitmapFactory.decodeFile(pathName);
        setBitmapToView(bitmap, mConvertView.findViewById(resId));
    }

    public void setImage(@IdRes int resId, Bitmap bitmap)
    {
        setBitmapToView(bitmap, mConvertView.findViewById(resId));
    }

    private void setBitmapToView(Bitmap bitmap, View view)
    {
        if (bitmap != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            }
        }
    }

}
