package com.wei.wanandroid.activity;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.widgets.CusImgView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class MainActivity extends BaseActivity
{
    @BindView(R.id.imgView_move)
    CusImgView mMoveImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        setImageUri();
        setRoundedImage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMoveImgView.smoothScrollTo(-400, 0);
//        mMoveImgView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
//        ObjectAnimator.ofFloat(mMoveImgView, "translationX", 0, 400).setDuration(3000).start();
        TextUtils.equals(null, null);
    }

    private void setRoundedImage()
    {
        int color = getResources().getColor(android.R.color.white);
        SimpleDraweeView draweeView = findViewById(R.id.draweeView_top);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(50);
        roundingParams.setBorder(color, 3.0f);
//        roundingParams.setRoundAsCircle(true);
        roundingParams.setOverlayColor(color);
//        draweeView.setImageResource(R.mipmap.ic_launcher);
        GenericDraweeHierarchy genericDraweeHierarchy = draweeView.getHierarchy();
        genericDraweeHierarchy.setRoundingParams(roundingParams);
        genericDraweeHierarchy.setProgressBarImage(new ProgressBarDrawable());

    }

    ControllerListener mControllerListener = new ControllerListener<ImageInfo>() {
        @Override
        public void onSubmit(String id, Object callerContext) {
            Log.e(TAG, "--- onSubmit ---" + ", " + id + ", " + callerContext);
        }

        @Override
        public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
            Log.e(TAG, "--- onFinalImageSet ---" + ", " + id + ", " + imageInfo);
            if (imageInfo == null)
            {
                return;
            }
            if (null != animatable)
            {
                animatable.start();
            }

            QualityInfo qualityInfo = imageInfo.getQualityInfo();
            FLog.d("Final image received! " +
                            "Size %d x %d",
                    "Quality level %d, good enough: %s, full quality: %s",
                    imageInfo.getWidth(),
                    imageInfo.getHeight(),
                    qualityInfo.getQuality(),
                    qualityInfo.isOfGoodEnoughQuality(),
                    qualityInfo.isOfFullQuality());
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            Log.e(TAG, "--- onIntermediateImageSet ---");
        }

        @Override
        public void onIntermediateImageFailed(String id, Throwable throwable) {
            Log.e(TAG, "--- onIntermediateImageFailed ---");
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            Log.e(TAG, "--- onFailure ---");
        }

        @Override
        public void onRelease(String id) {
            Log.e(TAG, "--- onRelease ---");
        }
    };
    Postprocessor mPostprocessor = new Postprocessor() {
        @Override
        public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Nullable
        @Override
        public CacheKey getPostprocessorCacheKey() {
            return null;
        }
    };

    void setImageUri()
    {
        Uri uri = Uri.parse("res://com.wei.wanandroid/" + R.mipmap.ic_wave); //("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");
        SimpleDraweeView draweeView = findViewById(R.id.draweeView_bottom);
        draweeView.setImageURI(uri);
        GenericDraweeHierarchy genericDraweeHierarchy = draweeView.getHierarchy();
        genericDraweeHierarchy.setProgressBarImage(new ProgressBarDrawable());

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(mPostprocessor)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .setOldController(draweeView.getController())
                .setControllerListener(mControllerListener)
                .build();
        draweeView.setController(mDraweeController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
