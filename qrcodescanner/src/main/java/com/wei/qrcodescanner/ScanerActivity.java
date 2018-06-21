package com.wei.qrcodescanner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zbar.lib.CaptureInterface;
import com.zbar.lib.camera.CameraManager;
import com.zbar.lib.decode.CaptureActivityHandler;

import java.io.IOException;

/**
 * 二维码/条形码扫描界面<br>
 * 调用 startActivityForResult(new Intent(getActivity(), ScanerActivity.class), REQUEST_SCANER_RESULT);<br>
 * 返回 if (requestCode == REQUEST_SCANER_RESULT && resultCode == Activity.RESULT_OK) { Bundle data = intent.getExtras(); if (data != null) { String code =
 * data.getString(ScanerActivity.KEY_SCAN_RESULT); Log.d(TAG, "扫描结果: " + code); T.showLong(getActivity(), "扫描结果: " + code);
 * <p/>
 * }
 * <p/>
 * }
 *
 * @author Jetch
 */
public class ScanerActivity  extends AppCompatActivity implements Callback, CaptureInterface {
    public static final String TAG = ScanerActivity.class.getSimpleName();

    /**
     * 扫描结果回调传递参数
     **/
    public static final String KEY_SCAN_RESULT = "scan_reuslt";
    public static final int REQUEST_SCANER_RESULT = 1;
    private CaptureActivityHandler handler;
    private boolean hasSurface;

    private boolean vibrate = true;
    private int x = 0;
    private int y = 0;
    private int cropWidth = 0;
    private int cropHeight = 0;
    private RelativeLayout mContainer = null;
    private RelativeLayout mCropLayout = null;
    private boolean isLandscape = false; //是否横屏

    private final int REQUEST_CODE_ASK_PERMISSION = 1;
    private SurfaceHolder mHolder;
    private Dialog mDialog;

    public static void qrScan(Activity context)
    {
        Intent intent = new Intent(context, ScanerActivity.class);
        context.startActivityForResult(intent, REQUEST_SCANER_RESULT);
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    @Override
    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qr_scan);
        // 初始化 CameraManager
        CameraManager.init(getApplication());
        hasSurface = false;

        mContainer = (RelativeLayout) findViewById(R.id.capture_containter);
        mCropLayout = (RelativeLayout) findViewById(R.id.capture_crop_layout);

        ImageView topMask = (ImageView) findViewById(R.id.top_mask);
        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        Log.d(TAG, "screenWidth:" + screenWidth);
        Log.d(TAG, "screenHeight:" + screenHeight);
        if (screenWidth > screenHeight) {
            Log.d(TAG, "横屏显示");
            isLandscape = true;
        } else {
            Log.d(TAG, "竖屏显示");
        }
        int adjustmentRowHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int screenMin = screenWidth > screenHeight ? screenHeight : screenWidth;
        int crop = (int) ((float) screenMin * 0.75f);
        int topMaskHeight = (screenHeight - crop) / 2 - adjustmentRowHeight;

        LayoutParams ml = topMask.getLayoutParams();
        ml.height = topMaskHeight;
        topMask.setLayoutParams(ml);

        LayoutParams cl = mCropLayout.getLayoutParams();
        cl.width = crop;
        cl.height = crop;
        mCropLayout.setLayoutParams(cl);

        ImageView mQrLineView = (ImageView) findViewById(R.id.capture_scan_line);
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(1500);

        mQrLineView.startAnimation(animation);
    }

    boolean flag = true;

    protected void light() {
        if (flag == true) {
            flag = false;
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            flag = true;
            // 关闪光灯
            CameraManager.get().offLight();
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.e(TAG, "--- onResume ---");
        initSurfaceView();
    }

    private void initSurfaceView()
    {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        if (CameraManager.get() != null)
        {
            CameraManager.get().closeDriver();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void handleDecode(String result)
    {
        playBeepSoundAndVibrate();

        Log.e(TAG, "--- handleDecode ---");
        //		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

        // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
        //handler.sendEmptyMessage(ZbarCommand.restart_preview);

        Log.e(TAG, result);
        Intent intent = getIntent();
        intent.putExtra(KEY_SCAN_RESULT, result);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder, isLandscape);

            Point point = CameraManager.get().getCameraResolution();

            int width = 0;
            int height = 0;
            if (!isLandscape) {//竖屏（默认）
                width = point.y;
                height = point.x;
            } else {//横屏
                width = point.x;
                height = point.y;
            }

            int x = mCropLayout.getLeft() * width / mContainer.getWidth();
            int y = mCropLayout.getTop() * height / mContainer.getHeight();

            int cropWidth = mCropLayout.getWidth() * width / mContainer.getWidth();
            int cropHeight = mCropLayout.getHeight() * height / mContainer.getHeight();

            setX(x);
            setY(y);
            setCropWidth(cropWidth);
            setCropHeight(cropHeight);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            if (Build.VERSION.SDK_INT < 23)
            {
                warnCamera();
            }
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(ScanerActivity.this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            if (Build.VERSION.SDK_INT >= 23)
            { // Android 6.0 + 动态申请权限
                if (PermissionController.grantedPermission(this, Manifest.permission.CAMERA))
                {
                    // 已授权
                    initCamera(holder);
                }
                else
                {
                    mHolder = holder;
                    // 未授权，弹提示框
                    PermissionController.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSION);
                }
            }
            else
            {
                initCamera(holder);
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    @Override
    public CaptureActivityHandler getCaptureActivityHandler() {
        return handler;
    }

    @Override
    public boolean isLandscape() {
        return isLandscape;
    }

    private void warnCamera()
    {
        showMsg("无法启动相机");
        mDialog = new AlertDialog.Builder(this)
                .setTitle("无法启动相机")
                .setMessage("请允许联享家访问摄像头后重试。")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            // 跳转到app详情设置界面
                            Uri uri = Uri.parse("package:" + getPackageName());
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                            startActivity(intent);
                            mDialog.dismiss();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                })
                .create();
        mDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_CODE_ASK_PERMISSION:
                Log.e(TAG, "grantResults : " + grantResults[0]);
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // 授权
                    initCamera(mHolder);
                }
                else
                {
                    // 拒绝
                    mDialog = new AlertDialog.Builder(this)
                            .setTitle("相机被拒绝")
                            .setMessage("您已拒绝启动相机功能，二维码扫描无法进行！如需开启请到权限中心设置！")
                            .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss();
                                    try {
                                        // 跳转到app详情设置界面
                                        Uri uri = Uri.parse("package:" + getPackageName());
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                                        startActivity(intent);
                                    }
                                    catch (Exception e)
                                    {
                                        Log.e(TAG, e.getMessage());
                                    }
                                }
                            })
                            .create();
                    mDialog.show();
                }
                break;
        }
    }

    public void showMsg(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}