package com.wei.wanandroid.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import com.wei.utillibrary.FileUtil;
import com.wei.wanandroid.service.JobHandlerService;
import com.wei.wanandroid.service.KeepAliveService;
import com.wei.wanandroid.R;
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
        Log.e(TAG, "--- onCreate ---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        ClassLoader classLoader = getClassLoader();
//        while (classLoader != null)
//        {
//            Log.e(TAG, classLoader.toString());
//            classLoader = classLoader.getParent();
//        }
//
//        startService(new Intent(this, KeepAliveService.class));
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("customscheme://com.hori.smartcommunity/notify_details?title=good news" +
//                "&content=this is a test notification."));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        String intentUri = intent.toUri(Intent.URI_INTENT_SCHEME);

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
        Log.e(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_lzlmain, menu);
        // 每次打开菜单都会回调onPrepareOptionsMenu，但onCreateOptionsMenu只回调一次
        menu.add(Menu.NONE, Menu.FIRST + 1, 0, "最后的菜单");
        // true 显示；false 不显示。因为 getParent() == null, 所以实际上 super.onPrepareOptionsMenu(menu) == true
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.e(TAG, "onPrepareOptionsMenu, parent == " + getParent());
        // true 显示；false 不显示。因为 getParent() == null, 所以实际上 super.onPrepareOptionsMenu(menu) == true
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, "onOptionsItemSelected, " + item.getItemId());
        switch (item.getItemId())
        {
            case R.id.action_about_us:
                Log.e(TAG, item.getTitle() + "");
//                startActivity(new Intent(this, RecyclerViewActivity.class));
                showDialog();
                break;

            case Menu.FIRST + 1:
                Log.e(TAG, "最后添加的菜单");
                break;

            default:
        }
        return true;
    }

    private void showDialog()
    {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("测试对话框")
                .show();
    }

    public void setPermission(View view)
    {
        Log.e(TAG, "--- setPermission ---");
        Intent mAccessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        mAccessibleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mAccessibleIntent);

        FileUtil.saveFile("hello , this is a test !");
        FileUtil.saveFile("I love beautyleg !");
    }
}
