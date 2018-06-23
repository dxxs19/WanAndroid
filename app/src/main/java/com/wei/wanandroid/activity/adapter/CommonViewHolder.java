package com.wei.wanandroid.activity.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void setImage(@IdRes int resId, String pathName)
    {
        Bitmap bitmap = BitmapFactory.decodeFile(pathName);
        if (bitmap != null) {
            View view = mConvertView.findViewById(resId);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            }
        }
    }

}
