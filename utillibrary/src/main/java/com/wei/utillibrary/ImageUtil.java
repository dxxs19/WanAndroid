package com.wei.utillibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 1.图片的尺寸，对应BitmapFactory.Option的inSampleSize值。
 * inSampleSize值表示缩略图大小为原图大小的几分之一。例如，inSampleSize==4时则缩略图的宽和高都为原图的1/4，相应的图片大小就为原始大小的1/16，如果inSampleSize值<=1时则取1。需要注意的是，inSampleSize值只能是2的整数次幂，如果不是的话则会向下取得最大的2的整数次幂。
 * 2.图片的格式，对应Bitmap的compress(Bitmap.CompressFormat format, int quality, OutputStream stream)方法中第一个参数。
 * CompressFormat类是个枚举，有三个取值：JPEG、PNG和WEBP。其中，PNG是无损格式（忽略质量设置），会导致方法中的第二个参数压缩质量失效，JPEG不解释，而WEBP格式是Google新推出的，据官方资料称“在质量相同的情况下，WEBP格式图像的体积要比JPEG格式图像小40%。”
 * 3.图片的质量，对应Bitmap的compress(Bitmap.CompressFormat format, int quality, OutputStream stream)方法中第二个参数，取值范围为0-100, 0表示压缩为最小质量，100表示压缩为最大质量(即保持原图质量)。
 */
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
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        Log.e(TAG, "width : height = " + reqWidth + " : " + reqHeight);
        Log.e(TAG, "outWidth : outHeight = " + w + " : " + h + ", inSampleSize = " + inSampleSize);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        // 尺寸压缩
        Bitmap inSampleBitmap = BitmapFactory.decodeFile(pathName, options);
//        // 格式压缩
//        inSampleBitmap.compress(Bitmap.CompressFormat.JPEG, 10, )
//        // 质量压缩
        return compressImage(inSampleBitmap);//压缩好比例大小后再进行质量压缩  ;
    }

    private static Bitmap compressImage(Bitmap image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while ( baos.toByteArray().length / 1024>100) {
            //重置baos即清空baos
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;//每次都减少10
        }
        Log.e(TAG, "options = " + options);

        // 无损压缩
//        image.compress(Bitmap.CompressFormat.PNG, 100, baos);

        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
}
