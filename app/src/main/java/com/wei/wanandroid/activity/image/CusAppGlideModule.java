package com.wei.wanandroid.activity.image;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * 此类不能少，但方法可以为空。
 * 缺少此类会报异常
 * @author: WEI
 * @date: 2018/3/26
 */
@GlideModule
public class CusAppGlideModule extends AppGlideModule
{
    private final static long CACHE_DEFAULTSIZE = 10 * 1024 * 1024;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
//        super.applyOptions(context, builder);
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide-cache", CACHE_DEFAULTSIZE));
        // 一般情况下，开发者是不需要自己去指定它们的大小的，因为Glide已经帮我们做好了。默认的内存缓存和bitmapPool的大小
        // 由MemorySizeCalculator根据当前设备的屏幕大小和可用内存计算得到
//        builder.setMemoryCache(new LruResourceCache(CACHE_DEFAULTSIZE));
//        builder.setBitmapPool(new LruBitmapPool(CACHE_DEFAULTSIZE));
        
    }
}
