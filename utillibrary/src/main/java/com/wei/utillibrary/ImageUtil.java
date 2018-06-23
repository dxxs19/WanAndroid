package com.wei.utillibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageUtil
{
    private static final String TAG = "ImageUtil";


    public static Bitmap getOptsBitmap(String pathName, int reqWidth, int reqHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度，即opts.width和opts.height。有了这两个参数，再通过一定的算法，即可得到一个恰当的inSampleSize。
        BitmapFactory.decodeFile(pathName, options);
        int w = options.outWidth;
        int h = options.outHeight;
        int inSampleSize = 1;
        if (w > reqWidth || h > reqHeight)
        {
            int widthRatio = Math.round( w / reqWidth);
            int heightRatio = Math.round( h / reqHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        Log.e(TAG, "width : height = " + reqWidth + " : " + reqHeight);
        Log.e(TAG, "outWidth : outHeight = " + w + " : " + h + ", inSampleSize = " + inSampleSize);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(pathName, options);
    }
}
